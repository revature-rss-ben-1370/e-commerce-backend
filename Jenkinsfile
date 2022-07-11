node {
  stage('SCM') {
    checkout scm
  }
  stage('SonarQube Analysis') {
    def mvn = tool 'Maven';
    withSonarQubeEnv('sonarserver') {
      sh "mvn sonar:sonar"
      sh "${mvn}/bin/mvn clean verify sonar:sonar -Dsonar.projectKey=test1"
    }
  }
}
