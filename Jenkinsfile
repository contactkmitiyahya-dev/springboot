pipeline {
    agent any

    environment {
        DOCKERHUB = credentials('DOCKERHUB')
    }

    stages {
        stage('Checkout & Build') {
            steps {
                git branch: 'springLearningProject', url: 'https://github.com/contactkmitiyahya-dev/springboot.git'
                dir('testProject') {
                    sh 'mvn clean install -DskipTests'
                }
            }
        }

        stage('Docker Build & Push') {
            steps {
                dir('testProject') {
                sh 'pwd'  // Affiche le répertoire actuel
                                sh 'ls -la'  // Liste tous les fichiers
                                sh 'find . -name "pom.xml"'
                    sh '''
                        echo "Login avec $DOCKERHUB_USR ..."
                        echo "$DOCKERHUB_PSW" | docker login -u "$DOCKERHUB_USR" --password-stdin

                        docker build -t kmitiyahya/springProjectTest:latest .
                        docker push kmitiyahya/springProjectTest:latest

                        echo "IMAGE PUBLIÉE → https://hub.docker.com/r/kmitiyahya/springProjectTest"
                    '''
                }
            }
        }
    }

    post {
        success {
            echo 'success'
        }
    }
}
