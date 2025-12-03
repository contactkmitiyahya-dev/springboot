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

                    // Simple build trigger detection (no restricted methods)
                    echo "Build Number: ${BUILD_NUMBER}"
                    echo "Build URL: ${BUILD_URL}"

                    // Check if this is a manual trigger
                    def isManual = currentBuild.getBuildCauses().toString().contains('UserIdCause')
                    if (isManual) {
                        echo "Trigger: MANUAL (User clicked Build Now)"
                    } else {
                        echo "Trigger: AUTOMATIC (SCM polling or webhook)"
                    }
                }

                sh '''
                    echo "=== Git Change Detection ==="

                    # Get current commit info
                    CURRENT_COMMIT=$(git rev-parse --short HEAD)
                    LAST_COMMIT_MSG=$(git log -1 --pretty=format:"%s")
                    LAST_COMMIT_AUTHOR=$(git log -1 --pretty=format:"%an")

                    echo "Current Commit: ${CURRENT_COMMIT}"
                    echo "Last Commit Message: ${LAST_COMMIT_MSG}"
                    echo "Last Commit Author: ${LAST_COMMIT_AUTHOR}"
                    echo "Commit Date: $(git log -1 --pretty=format:"%cd" --date=format:"%Y-%m-%d %H:%M:%S")"

                    # Check for new commits since last build
                    echo ""
                    echo "=== PUSH vs PULL Detection ==="
                    if [ -f ".git/last_build_commit" ]; then
                        LAST_BUILD_COMMIT=$(cat .git/last_build_commit)
                        echo "Previous build commit: ${LAST_BUILD_COMMIT}"
                        echo "Current build commit: ${CURRENT_COMMIT}"

                        if [ "${LAST_BUILD_COMMIT}" = "${CURRENT_COMMIT}" ]; then
                            echo "🔁 Operation: PULL/REBUILD (same commit)"
                            echo "This is likely a rebuild or manual trigger"
                        else
                            echo "🚀 Operation: PUSH (new commits detected)"
                            echo ""
                            echo "New commits since last build:"
                            git log --oneline ${LAST_BUILD_COMMIT}..${CURRENT_COMMIT} 2>/dev/null || echo "First time comparing commits"

                            echo ""
                            echo "Files changed in latest commit:"
                            git diff-tree --no-commit-id --name-only -r ${CURRENT_COMMIT} 2>/dev/null || echo "Could not get file list"
                        fi
                    else
                        echo "📋 First build or no previous commit record"
                        echo "Operation: INITIAL CHECKOUT"
                    fi

                    # Save current commit for next comparison
                    echo "${CURRENT_COMMIT}" > .git/last_build_commit

                    # Show branch info
                    echo ""
                    echo "=== Repository Info ==="
                    echo "Branch: $(git branch --show-current 2>/dev/null || echo 'detached HEAD')"
                    echo "Remote: $(git remote -v | head -1)"
                    echo "Total commits: $(git rev-list --count HEAD)"

                    # Show recent commits
                    echo ""
                    echo "Recent commits (last 5):"
                    git log --oneline -5
                '''
            }
        }

        stage('Build & Test') {
            steps {
                sh '''
                    echo "=== BUILD STARTING ==="
                    echo "Build time: $(date)"

                    if [ -f "pom.xml" ]; then
                        echo "Building Spring Boot application..."
                        mvn clean install -DskipTests

                        # List created artifacts
                        echo ""
                        echo "=== BUILD ARTIFACTS ==="
                        if [ -d "target" ]; then
                            JAR_FILES=$(find target -name "*.jar" -type f | wc -l)
                            if [ ${JAR_FILES} -gt 0 ]; then
                                echo "Found ${JAR_FILES} JAR file(s):"
                                find target -name "*.jar" -type f -printf "  📦 %p\\n"
                            else
                                echo "No JAR files found in target/"
                            fi
                        else
                            echo "Target directory not found"
                        fi
                    else
                        echo "ERROR: pom.xml not found!"
                        ls -la
                        exit 1
                    fi
                '''
            }
        }

        stage('Post Build') {
            steps {
                sh '''
                    echo "=== BUILD COMPLETED ==="
                    echo "Completion time: $(date)"

                    # Create build summary
                    echo "=== BUILD SUMMARY ===" > build_summary.txt
                    echo "Project: $(basename $(pwd))" >> build_summary.txt
                    echo "Build: ${BUILD_NUMBER}" >> build_summary.txt
                    echo "Commit: $(git rev-parse --short HEAD)" >> build_summary.txt
                    echo "Status: ${BUILD_RESULT:-UNKNOWN}" >> build_summary.txt
                    echo "Timestamp: $(date)" >> build_summary.txt

                    cat build_summary.txt
                '''

                // Save build info
                archiveArtifacts artifacts: 'build_summary.txt, target/*.jar', fingerprint: true
            }
        }
    }

    post {
        always {
            echo "====== PIPELINE COMPLETED ======"
            script {
                echo "Duration: ${currentBuild.durationString}"
                echo "Result: ${currentBuild.currentResult}"

                // Cleanup
                sh 'rm -f build_summary.txt 2>/dev/null || true'
            }
        }
        success {
            echo "✅ ===== PIPELINE SUCCESS ====="
            script {
                // Success actions
                echo "Build ${BUILD_NUMBER} completed successfully!"
            }
        }
        failure {
            echo "❌ ===== PIPELINE FAILED ====="
            script {
                // Failure actions
                echo "Build ${BUILD_NUMBER} failed!"
            }
        }
        changed {
            echo "🔄 ===== STATUS CHANGED ====="
        }
    }
}