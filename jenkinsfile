pipeline {
    agent any
    
    triggers {
        pollSCM('H/5 * * * *')  // Poll Git every 5 minutes for changes
    }
    
    options {
        timeout(time: 5, unit: 'MINUTES')
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
        
        stage('Display Changes') {
            steps {
                script {
                    echo "=== Recent Changes in Git Repository ==="
                    
                    // Get the change log
                    def changeLogSets = currentBuild.changeSets
                    
                    if (changeLogSets.isEmpty()) {
                        echo "No changes detected since last build"
                    } else {
                        echo "Changes detected! Total change sets: ${changeLogSets.size()}"
                        
                        int commitCount = 0
                        for (changeLogSet in changeLogSets) {
                            for (entry in changeLogSet.items) {
                                commitCount++
                                echo ""
                                echo "Commit #${commitCount}:"
                                echo "  Commit ID: ${entry.commitId}"
                                echo "  Author: ${entry.author}"
                                echo "  Date: ${new Date(entry.timestamp)}"
                                echo "  Message: ${entry.msg}"
                                
                                if (!entry.affectedFiles.isEmpty()) {
                                    echo "  Changed files:"
                                    for (file in entry.affectedFiles) {
                                        echo "    - ${file.path}"
                                    }
                                }
                                echo "----------------------------------------"
                            }
                        }
                        echo "Total commits in this build: ${commitCount}"
                    }
                }
            }
        }
        
        stage('Code Build') {
            steps {
                sh '''
                    if [ -f "pom.xml" ]; then
                        echo "Building Maven project..."
                        mvn clean install -DskipTests
                    else
                        echo "No pom.xml found"
                        ls -la
                        exit 1
                    fi
                '''
            }
        }
    }
    
    post {
        always {
            echo "====== Pipeline completed ======"
        }
        success {
            echo "===== Pipeline executed successfully ====="
        }
        failure {
            echo "====== Pipeline execution failed ====="
        }
    }
}
