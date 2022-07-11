node {
  stage('SCM') {
    checkout scm
  }
  stage('SonarQube Analysis') {
    def mvn = tool 'Maven';
    withSonarQubeEnv('sonarserver') {
      sh "${mvn}/bin/mvn clean verify sonar:sonar -Dsonar.projectKey=test2 -Dsonar.host.url=http://add508a3df0be46a6bf2d07ad7a3af9f-951951760.us-east-1.elb.amazonaws.com:9000/ -Dsonar.login=squ_e037b1708ebb3a9e50b2a76421dd07dddc73e175"
    }
  }
}