pipeline {
    agent {
        kubernetes {
          label 'idk'
          defaultContainer 'jnlp'
          yaml """
apiVersion: v1
kind: Pod
metadata:
labels:
  component: ci
spec:
  # Use service account that can deploy to all namespaces
  serviceAccountName: jenkins
  containers:
  - name: docker
    image: docker:latest
    command:
    - cat
    tty: true
    volumeMounts:
    - mountPath: /var/run/docker.sock
      name: docker-sock
  - name
  volumes:
    - name: docker-sock
      hostPath:
        path: /var/run/docker.sock
"""
}
    }

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
                sh "export DB_PLATFORM=org.hibernate.dialect.H2Dialect"
                sh "export DB_URL=jdbc:h2:mem:test;MODE=PostgreSQL"
                sh "export DB_DRIVER=org.h2.Driver"
                sh "mvn package"
            }
        }
        stage('Test') {
            steps {
                echo 'Testing..'
            }
        }        
        stage('Building Docker Image') {
            steps {
                container('docker'){
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
                echo 'Pushing..'
                container('docker'){
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
                echo 'Deploying....'
                script {
                    sh "kubectl set image -n p3-space deployment/back-end-deployment back-end-deployment=$registry:$currentBuild.number"
                }
            }
        }
    }
}