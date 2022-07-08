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

                    sh "docker run --name myjenkins -p 8080:8080 -p 50000:50000 \
                        -v /var/jenkins_home \
                            jenkins -v /var/run/docker.sock:/var/run/docker.sock"

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