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
                
                sh "mvn package"
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