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
        liveBranch = ''
    }
    stages {
        stage{'Obtain live branch'} {
            steps {
                container{'kubectl'} {
                    script{
                        liveBranch = 'kubectl get svc back-end-service -o=jsonpath=\'{.spec.selector.app}\''
                    }
                }
            }
        }
    //     stage('Build') {
    //         steps {
    //             container('maven'){
    //                 echo 'Building..'
    //                 sh "export DB_PLATFORM=org.hibernate.dialect.H2Dialect"
    //                 sh "export DB_URL=jdbc:h2:mem:test;MODE=PostgreSQL"
    //                 sh "export DB_DRIVER=org.h2.Driver"
    //                 sh "mvn package"
    //             }
    //         }
    //     }
    //     stage('Test') {
    //         steps {
    //             echo 'Testing..'
    //         }
    //     }
    //     stage('Building Docker Image') {
    //         steps {
    //             container('docker') {
    //                 echo 'Building Image..'
    //                 script {
    //                     echo 'NOW BUILDING DOCKER IMAGE'
    //                     dockerImage = docker.build "$registry"
    //                 }
    //             }
    //         }
    //     }
    //     stage('Pushing Docker Image') {
    //         steps {
    //             container('docker') {
    //                 echo 'Pushing..'
    //                     script {
    //                         echo "NOW PUSHING TO DOCKER HUB"
    //                         docker.withRegistry('', dockerHubCredentials){
    //                             //dbDockerImage.push("latest")
    //                             dockerImage.push("$currentBuild.number")
    //                             dockerImage.push("latest")
    //                         }
    //                     }
    //                 }
    //         }
    //     }
        stage('Deploy') {
            steps {
                container('kubectl') {
                    echo "$liveBranch"
                    //sh 'kubectl delete -f e-commerce-back-end-deployment.yml -n p3-space'
                    // sh 'kubectl apply -f e-commerce-back-end-deployment.yml -n p3-space'
                }
            }
        }
//         stage('smoke-test'){
//             steps {
//                 container('k6'){
//                     sh 'k6 run smoke-test.js'
//                 }
//             }
//         }
    }
}