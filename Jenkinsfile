pipeline {
    agent any

    environment {
        registry = 'elrintowser/p3-backend'
    }

    stages {
        stage('Build') {
            agent {
                label = "maven"
            }
            steps {
                echo 'Building..'
                
                sh "mvn package"

            }
        }
        stage('Test') {
            steps {
                echo 'Testing..'
            }
        }
        stage('Deploy') {
            steps {
                echo 'Deploying....'
            }
        }
    }
}