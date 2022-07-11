pipeline{
  stages{
    stage('Quality Check'){
      steps{
          script{
            withSonarQubeEnv('sonarserver'){
             sh "mvn sonar:sonar"
                    }
                  sh "mvn clean install"
          }
      }

    }
  }


}
