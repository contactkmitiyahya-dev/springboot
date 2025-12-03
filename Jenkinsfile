pipeline {
    agent any
    
    triggers {
        pollSCM('H/5 * * * *')  // Poll Git every 5 minutes
    }

    options {
        timeout(time: 5, unit: 'MINUTES')
        timestamps()  // Adds timestamps to console output
    }

    environment {
        APP_ENV = "DEV"
        GIT_OPERATION = "UNKNOWN"
    }

    stages {
        stage('Code Checkout') {
            steps {
                checkout([
                    $class: 'GitSCM',
                    branches: [[name: '*/springLearningProject']],
                    extensions: [
                        [$class: 'CleanCheckout'],
                        [$class: 'CloneOption', depth: 1, shallow: true],
                        [$class: 'BuildChooserSetting', buildChooser: [$class: 'AncestryBuildChooser', ancestorCommitSha: '', maximumAgeInDays: 0]]
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

                    // Get build causes
                    def buildCauses = currentBuild.getBuildCauses()
                    echo "Build triggered by: ${buildCauses}"

                    // Determine Git operation type
                    def changeLogSets = currentBuild.changeSets
                    if (!changeLogSets.isEmpty()) {
                        env.GIT_OPERATION = "PUSH"
                        echo "Git Operation: PUSH (new commits detected)"
                    } else {
                        env.GIT_OPERATION = "PULL/OTHER"
                        echo "Git Operation: PULL or other trigger (no new commits)"
                    }

                    // Get current branch info
                    sh '''
                        echo "=== Current Git Status ==="
                        git branch -a
                        echo ""
                        echo "=== Remote Information ==="
                        git remote -v
                        echo ""
                        echo "=== Last 5 Commits ==="
                        git log --oneline -5
                        echo ""
                        echo "=== Unpushed commits (if any) ==="
                        git log origin/springLearningProject..springLearningProject --oneline || echo "No unpushed commits"
                    '''

                    // Show changed files in detail
                    if (!changeLogSets.isEmpty()) {
                        echo ""
                        echo "=== DETAILED CHANGE ANALYSIS ==="
                        echo "Total change sets: ${changeLogSets.size()}"

                        int totalCommits = 0
                        int totalFiles = 0
                        def authors = [] as Set
                        def fileTypes = [:] as Map

                        for (changeLogSet in changeLogSets) {
                            for (entry in changeLogSet.items) {
                                totalCommits++
                                authors.add(entry.author.toString())

                                echo ""
                                echo "📌 Commit #${totalCommits}:"
                                echo "   Hash: ${entry.commitId.substring(0, 8)}"
                                echo "   Author: ${entry.author}"
                                echo "   Date: ${new Date(entry.timestamp).format('yyyy-MM-dd HH:mm:ss')}"
                                echo "   Message: ${entry.msg}"

                                if (!entry.affectedFiles.isEmpty()) {
                                    echo "   📁 Changed files (${entry.affectedFiles.size()}):"
                                    for (file in entry.affectedFiles) {
                                        totalFiles++
                                        def fileName = file.path
                                        def ext = fileName.contains('.') ? fileName.substring(fileName.lastIndexOf('.') + 1) : 'no-extension'
                                        fileTypes[ext] = fileTypes.getOrDefault(ext, 0) + 1

                                        echo "      • ${file.editType.name}: ${file.path}"
                                    }
                                }
                                echo "   ─────────────────────────"
                            }
                        }

                        // Summary statistics
                        echo ""
                        echo "=== CHANGE SUMMARY ==="
                        echo "Total Commits: ${totalCommits}"
                        echo "Total Files Changed: ${totalFiles}"
                        echo "Authors: ${authors.join(', ')}"
                        echo "File Types Changed:"
                        fileTypes.each { ext, count ->
                            echo "   ${ext.padRight(15)}: ${count} file(s)"
                        }

                        // Detect if it's a merge commit
                        sh '''
                            echo ""
                            echo "=== Merge Analysis ==="
                            if git log -1 --pretty=format:"%P" | grep -q " "; then
                                echo "🔀 Latest commit is a MERGE commit"
                                echo "Parents: $(git log -1 --pretty=format:"%P")"
                            else
                                echo "📝 Latest commit is a regular commit"
                            fi
                        '''
                    } else {
                        echo "=== NO NEW COMMITS DETECTED ==="
                        echo "This build was triggered by:"
                        echo "1. Manual trigger (Build Now)"
                        echo "2. Schedule/CRON"
                        echo "3. Git PULL operation"
                        echo "4. Other Jenkins trigger"

                        // Check if it's a rebuild
                        sh '''
                            echo ""
                            echo "=== Previous Build Comparison ==="
                            current_commit=$(git rev-parse HEAD)
                            echo "Current commit: ${current_commit}"

                            # Try to get previous build commit
                            if [ -f ".git/previous_commit" ]; then
                                previous_commit=$(cat .git/previous_commit)
                                echo "Previous commit: ${previous_commit}"

                                if [ "${current_commit}" != "${previous_commit}" ]; then
                                    echo "✅ Different commit - possibly a PULL operation"
                                    echo "Changes since last build:"
                                    git diff --name-only ${previous_commit} ${current_commit} 2>/dev/null || echo "Could not compare commits"
                                else
                                    echo "🔄 Same commit - rebuild of existing code"
                                fi
                            else
                                echo "No previous commit info available"
                            fi

                            # Save current commit for next build
                            echo "${current_commit}" > .git/previous_commit
                        '''
                    }
                }
            }
        }

        stage('Build & Test') {
            steps {
                script {
                    echo "=== BUILDING WITH GIT CONTEXT ==="
                    echo "Operation Type: ${env.GIT_OPERATION}"
                    echo "Build Number: ${BUILD_NUMBER}"
                    echo "Build URL: ${BUILD_URL}"
                }

                sh '''
                    if [ -f "pom.xml" ]; then
                        echo "🚀 Building Maven project..."
                        echo "Git Branch: $(git branch --show-current)"
                        echo "Git Commit: $(git rev-parse --short HEAD)"
                        echo "Build Date: $(date)"

                        mvn clean install -DskipTests

                        # Generate build info
                        echo "=== BUILD ARTIFACTS ==="
                        find target -name "*.jar" -type f | while read jar; do
                            echo "📦 JAR: ${jar} ($(stat -c%s "${jar}") bytes)"
                        done
                    else
                        echo "❌ No pom.xml found"
                        ls -la
                        exit 1
                    fi
                '''
            }

            post {
                success {
                    script {
                        echo "✅ BUILD SUCCESSFUL for ${env.GIT_OPERATION} operation"
                        // You could add notifications here
                    }
                }
                failure {
                    script {
                        echo "❌ BUILD FAILED for ${env.GIT_OPERATION} operation"
                        // You could add failure notifications here
                    }
                }
            }
        }
    }

    post {
        always {
            echo "====== PIPELINE COMPLETED ======"
            script {
                def duration = currentBuild.durationString
                echo "Build Duration: ${duration}"
                echo "Final Status: ${currentBuild.currentResult}"
                echo "Git Operation Detected: ${env.GIT_OPERATION}"

                // Archive artifacts
                archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
                junit 'target/surefire-reports/*.xml'
            }
        }
        success {
            echo "🎉 ===== PIPELINE EXECUTED SUCCESSFULLY ====="
            script {
                // Success notification example
                echo "Would send success notification for ${env.GIT_OPERATION} operation"
            }
        }
        failure {
            echo "💥 ===== PIPELINE EXECUTION FAILED ====="
            script {
                // Failure notification example
                echo "Would send failure notification for ${env.GIT_OPERATION} operation"
            }
        }
        changed {
            echo "🔄 ===== BUILD STATUS CHANGED ====="
        }
    }
}