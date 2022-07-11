pipeline{
  stages{
    stage('Quality Check'){
      steps{
          script{
            withSonarQubeEnv('sonarserver'){
             sh "mvn sonar:sonar"
                    }
            timeout(time: 1, unit: 'HOURS'){
            def qg = waitForQualityGate()
                if (qg.status =! 'OK'){
                  error "Pipelin aborted due to quality gate faillure: $(qg.status)"

                }  
                  }
                  sh "mvn clean install"
          }
      }

    }
  }


}
