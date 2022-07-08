pipeline {
    agent any

    environment {
        registry = 'elrintowser/p3-backend'
        dockerHubCredentials = 'dockerHubCredentials'
        dockerImage = ''
    }

    stages {
        stage('Build') {
            agent {
                label "maven"
            }
            steps {
                echo 'Building..'
                
                sh "mvn package"
            }
        }
        stage('Building Docker Image') {
            agent {
                label "docker"
            }
            steps {
                echo 'Building Image..'
                script {
                    echo 'NOW BUILDING DOCKER IMAGE'
                    dockerImage = docker.build "$registry"
                }


            }
        }
        stage('Pushing Docker Image') {
            steps {
                echo 'Pushing..'
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