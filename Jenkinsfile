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
        newColor = ''
    }
    stages {
        stage('Obtain live branch') {
            steps {
                container('kubectl') {
                    script{
                        liveBranch = "kubectl get svc back-end-service -n bg -o=jsonpath='{.spec.selector.color}'"
                        if (liveBranch.equals("blue")) {
                                newColor = "green"
                        } else {
                                newColor = "blue"
                        }
                    }
                }
            }
        }
        // stage('Build') {
        //     steps {
        //         container('maven'){
        //             echo 'Building..'
        //             sh "export DB_PLATFORM=org.hibernate.dialect.H2Dialect"
        //             sh "export DB_URL=jdbc:h2:mem:test;MODE=PostgreSQL"
        //             sh "export DB_DRIVER=org.h2.Driver"
        //             sh "mvn package"
        //         }
        //     }
        // }
        // stage('Test') {
        //     steps {
        //         echo 'Testing..'
        //     }
        // }
        // stage('Building Docker Image') {
        //     steps {
        //         container('docker') {
        //             echo 'Building Image..'
        //             script {
        //                 echo 'NOW BUILDING DOCKER IMAGE'
        //                 sh 'ls -l'
        //                 sh 'ls ./target -l'
        //                 sh 'chmod 777 ./target/e-commerce-1.0.jar'
        //                 dockerImage = docker.build "$registry"
        //             }
        //         }
        //     }
        // }
        // stage('Pushing Docker Image') {
        //     steps {
        //         container('docker') {
        //             echo 'Pushing..'
        //                 script {
        //                     echo "NOW PUSHING TO DOCKER HUB"
        //                     docker.withRegistry('', dockerHubCredentials){
        //                         dockerImage.push("$currentBuild.number")
        //                         dockerImage.push("latest")
        //                     }
        //                 }
        //             }
        //     }
        // }
        stage('Deploy') {
            steps {
                container('kubectl') {
                    echo "$liveBranch"
                    echo "$newColor"
                    // sh 'kubectl delete -f ./resources/back-end-deployment.yml -n p3-space'
                    // sh 'kubectl apply -f ./resources/back-end-deployment-blue.yml -n p3-space'
                    sh "kubectl apply -f ./resources/back-end-deployment-$newColor" + ".yml -n bg"
                    // sh 'kubectl apply -f ./resources/back-end-deployment-green.yml -n p3-space'
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