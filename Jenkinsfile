pipeline {
    agent any

    environment {
        DOCKERHUB = credentials('DOCKERHUB')
    }

    stages {
        stage('Checkout & Build') {
            steps {
                git branch: 'springLearningProject', url: 'https://github.com/contactkmitiyahya-dev/springboot.git'

                // Commandes de debug
                sh 'echo "=== CONTENU DU RÉPERTOIRE ==="'
                sh 'ls -la'
                sh 'echo "=== RECHERCHE DU POM.XML ==="'
                sh 'find . -name "pom.xml"'

                // Build Maven (sans dir)
                sh 'mvn clean install -DskipTests'
            }
        }

        stage('Docker Build & Push') {
            steps {
                sh '''
                    echo "Login avec $DOCKERHUB_USR ..."
                    echo "$DOCKERHUB_PSW" | docker login -u "$DOCKERHUB_USR" --password-stdin

                    docker build -t kmitiyahya/springprojecttest:latest .
                    docker push kmitiyahya/springprojecttest:latest

                    echo "IMAGE PUBLIÉE → https://hub.docker.com/r/kmitiyahya/springprojecttest"
                '''
            }
        }
    }

    post {
        success {
            echo 'Pipeline terminé avec succès !'
        }
        failure {
            echo 'Le pipeline a échoué.'
        }
    }
}