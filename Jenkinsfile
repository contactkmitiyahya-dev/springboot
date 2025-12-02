pipeline {
    agent any
    
    options {
        timeout(time: 1, unit: 'SECONDS')
    }
    
    environment {
        APP_ENV = "DEV"
    }
    
    stages {
        stage('Code Checkout') {
            steps {
                git branch: 'springLearningProject',
                url: 'https://github.com/contactkmitiyahya-dev/springboot',
                credentialsId: '9d302f79-f34b-450c-89b4-37bd25e34a71'
            }
        }
        
        stage('Code Build') {
            steps {
                sh '''
                    if [ -f "pom.xml" ]; then
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
