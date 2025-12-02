pipeline {
    agent any
    stages {
        stage('Check Tools') {
            steps {
                sh '''
                    echo "Checking for Maven..."
                    which mvn || echo "mvn not found"
                    ls -la /opt/ || echo "/opt not accessible"
                    ls -la /usr/local/ || echo "/usr/local not accessible"
                '''
            }
        }
    }
}
