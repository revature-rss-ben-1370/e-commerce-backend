pipeline {
    agent {
        kubernetes {
          label 'idk'
          yamlFile 'pipeline-pod.yml'
        }
    }
    environment {
        registry = 'elrintowser/p3-backend'
        dockerHubCredentials = 'dockerHubCredentials'
        dockerImage = ''

    }

    stages {
        stage('Build') {
            steps {
                container('maven'){
                    echo 'Building..'
                    sh "export DB_PLATFORM=org.hibernate.dialect.H2Dialect"
                    sh "export DB_URL=jdbc:h2:mem:test;MODE=PostgreSQL"
                    sh "export DB_DRIVER=org.h2.Driver"
                    sh "mvn package"
                }
            }

        }
        stage('Test') {
            steps {
                echo 'Testing..'
            }
        }        
        stage('Building Docker Image') {
            steps {
                container('docker') {
                    echo 'Building Image..'
                    script {
                        echo 'NOW BUILDING DOCKER IMAGE'
                        dockerImage = docker.build "$registry"
                    }
                }
            }
        }
        stage('Pushing Docker Image') {
            steps {
                container('docker') {
                    echo 'Pushing..'
                        script {
                            echo "NOW PUSHING TO DOCKER HUB"
                            docker.withRegistry('', dockerHubCredentials){
                                //dbDockerImage.push("latest")
                                dockerImage.push("$currentBuild.number")
                                dockerImage.push("latest")
                            }
                        }
                    }
            }
        }
        stage('Deploy') {
            steps {
                echo 'Test'
                // withKubeConfig(caCertificate: '', clusterName: '', contextName: '', credentialsId: '', namespace: 'p3-space', serverUrl: '') {
                //     sh "kubectl set image -n p3-space deployment/back-end-deployment back-end-deployment=$registry:$currentBuild.number"
                //  }
            }
        }
    }
}