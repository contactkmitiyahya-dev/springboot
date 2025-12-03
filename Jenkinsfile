pipeline {
    agent any
    
    triggers {
        pollSCM('H/5 * * * *')
    }

    options {
        timeout(time: 5, unit: 'MINUTES')
        timestamps()
    }

    environment {
        APP_ENV = "DEV"
    }

    stages {
        stage('Code Checkout') {
            steps {
                checkout([
                    $class: 'GitSCM',
                    branches: [[name: '*/springLearningProject']],
                    extensions: [
                        [$class: 'CleanCheckout'],
                        [$class: 'CloneOption', depth: 1, shallow: true]
                    ],
                    userRemoteConfigs: [[
                        url: 'https://github.com/contactkmitiyahya-dev/springboot',
                        credentialsId: '9d302f79-f34b-450c-89b4-37bd25e34a71'
                    ]]
                ])
            }
        }

        stage('Analyze Git Changes') {
            steps {
                script {
                    echo "=== GIT REPOSITORY ANALYSIS ==="

                    // Get build causes safely
                    def causes = []
                    currentBuild.rawBuild.getCauses().each { cause ->
                        causes.add(cause.getShortDescription())
                    }
                    echo "Build triggered by: ${causes.join(', ')}"

                    // Use shell commands to analyze Git instead of Java objects
                    sh '''
                        echo "=== Git Status Analysis ==="

                        # Check current commit
                        CURRENT_COMMIT=$(git rev-parse --short HEAD)
                        echo "Current Commit: ${CURRENT_COMMIT}"

                        # Get last commit message
                        LAST_COMMIT_MSG=$(git log -1 --pretty=format:"%s")
                        LAST_COMMIT_AUTHOR=$(git log -1 --pretty=format:"%an")
                        LAST_COMMIT_DATE=$(git log -1 --pretty=format:"%cd" --date=format:"%Y-%m-%d %H:%M:%S")
                        echo "Last Commit: ${LAST_COMMIT_MSG}"
                        echo "Author: ${LAST_COMMIT_AUTHOR}"
                        echo "Date: ${LAST_COMMIT_DATE}"

                        # Check if there are new commits since last build
                        echo ""
                        echo "=== Change Detection ==="
                        if [ -f ".git/last_build_commit" ]; then
                            LAST_BUILD_COMMIT=$(cat .git/last_build_commit)
                            echo "Previous build commit: ${LAST_BUILD_COMMIT}"
                            echo "Current build commit: ${CURRENT_COMMIT}"

                            if [ "${LAST_BUILD_COMMIT}" != "${CURRENT_COMMIT}" ]; then
                                echo "✅ NEW COMMITS DETECTED (PUSH operation)"
                                echo "Changes since last build:"
                                git log --oneline ${LAST_BUILD_COMMIT}..${CURRENT_COMMIT} 2>/dev/null || echo "Could not get diff (maybe first build)"

                                # Show changed files
                                echo ""
                                echo "Changed files in latest commit:"
                                git diff-tree --no-commit-id --name-only -r ${CURRENT_COMMIT}
                            else
                                echo "🔄 SAME COMMIT (PULL/REBUILD operation)"
                            fi
                        else
                            echo "📋 FIRST BUILD or no previous commit info"
                            echo "Operation: Initial checkout or manual trigger"
                        fi

                        # Save current commit for next build
                        echo "${CURRENT_COMMIT}" > .git/last_build_commit

                        # Get branch info
                        echo ""
                        echo "=== Branch Information ==="
                        git branch -a | grep "*" || echo "Detached HEAD"
                        git remote -v

                        # Get commit stats
                        echo ""
                        echo "=== Repository Stats ==="
                        TOTAL_COMMITS=$(git rev-list --count HEAD)
                        echo "Total commits: ${TOTAL_COMMITS}"

                        # Last 3 commits
                        echo ""
                        echo "Last 3 commits:"
                        git log --oneline -3
                    '''

                    // Simple change detection without serialization issues
                    try {
                        def changeLogSets = currentBuild.changeSets
                        if (changeLogSets && !changeLogSets.isEmpty()) {
                            echo "📥 Git Operation: PUSH detected (through Jenkins API)"
                        } else {
                            echo "🔄 Git Operation: PULL/REBUILD/MANUAL detected"
                        }
                    } catch (Exception e) {
                        echo "⚠️ Could not analyze changes via API: ${e.message}"
                    }
                }
            }
        }

        stage('Build & Test') {
            steps {
                script {
                    echo "=== BUILDING PROJECT ==="
                    echo "Build Number: ${BUILD_NUMBER}"
                    echo "Build URL: ${BUILD_URL}"
                }

                sh '''
                    if [ -f "pom.xml" ]; then
                        echo "🚀 Building Spring Boot application..."
                        echo "Build started at: $(date)"

                        mvn clean install -DskipTests

                        # Show build artifacts
                        echo ""
                        echo "=== BUILD ARTIFACTS ==="
                        if [ -d "target" ]; then
                            find target -name "*.jar" -type f -exec echo "📦 {}" \;
                            JAR_COUNT=$(find target -name "*.jar" -type f | wc -l)
                            echo "Total JAR files: ${JAR_COUNT}"
                        else
                            echo "No target directory found"
                        fi
                    else
                        echo "❌ ERROR: pom.xml not found!"
                        echo "Current directory contents:"
                        ls -la
                        exit 1
                    fi
                '''
            }

            post {
                success {
                    echo "✅ BUILD SUCCESSFUL"
                }
                failure {
                    echo "❌ BUILD FAILED"
                }
            }
        }

        stage('Post Build Analysis') {
            steps {
                sh '''
                    echo "=== POST-BUILD ANALYSIS ==="
                    echo "Build completed at: $(date)"

                    # Check if build created expected files
                    if [ -f "target/testProject-0.0.1-SNAPSHOT.jar" ]; then
                        JAR_SIZE=$(stat -c%s "target/testProject-0.0.1-SNAPSHOT.jar")
                        echo "Main JAR created: target/testProject-0.0.1-SNAPSHOT.jar"
                        echo "JAR size: ${JAR_SIZE} bytes ($((${JAR_SIZE}/1024/1024)) MB)"
                    else
                        echo "Warning: Expected JAR not found in target/"
                    fi

                    # Save build info
                    echo "BUILD_INFO" > build_info.txt
                    echo "Timestamp: $(date)" >> build_info.txt
                    echo "Commit: $(git rev-parse --short HEAD)" >> build_info.txt
                    echo "Branch: $(git branch --show-current 2>/dev/null || echo 'detached')" >> build_info.txt
                    echo "Build Number: ${BUILD_NUMBER}" >> build_info.txt
                '''
            }
        }
    }

    post {
        always {
            echo "====== PIPELINE COMPLETED ======"
            script {
                def duration = currentBuild.durationString
                echo "Total Duration: ${duration}"
                echo "Final Result: ${currentBuild.currentResult}"

                // Archive artifacts
                archiveArtifacts artifacts: 'target/*.jar, build_info.txt', fingerprint: true

                // Clean up
                sh 'rm -f build_info.txt 2>/dev/null || true'
            }
        }
        success {
            echo "🎉 ===== PIPELINE SUCCESS ====="
        }
        failure {
            echo "💥 ===== PIPELINE FAILED ====="
        }
        changed {
            echo "🔄 ===== STATUS CHANGED ====="
        }
    }
}