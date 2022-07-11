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
                        liveBranch = sh(script:"kubectl get svc back-end-service -n bg -o=jsonpath='{.spec.selector.color}'", returnStdout:true).trim()
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

        stage('smoke-test'){
            steps {
                // container('k6'){
                //     sh 'k6 run smoke-test.js'
                // }
                echo 'doing smoke test'
            }
        }

        stage('Production Approve Request'){
            steps {
                echo 'test'
                script {
                    try {
                        approved = input message: 'Deploy to production?', ok: 'Continue',
                            parameters: [choice(name: 'approved', choices: 'Yes\nNo', description: 'Deploy this build to production')]
                        if(approved != 'Yes'){
                            error('Build not approved')
                        }
                    } catch (error){
                        error('Build not approved in time')
                    }
                }
            }
        }

        stage('Deploy to Production') { //Switching service
            steps {
                container('kubectl') {
                    script {
                        sh 'kubectl patch -f ./resources/back-end-service.yml -p ' + '{"spec":{"selector":{"color":"' + "$newColor" + '"' + "}}}'"  
                    }
                }
            }
        }
    }
}