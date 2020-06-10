pipeline{
    agent master
    tools {
        gradle 'Gradle 2.14'
    }
    stages{
        stage("Build"){
            steps{
                configFileProvider([configFile(fileId: 'hibernate-cfg-twittercitymod', targetLocation: 'src/main/resources/assets/tc/hibernate.cfg.xml')]) {
                    // some block
                }  
                sh 'gradle build'
                
            }
        }
    }
    post{
        always{
            slackSend message: 'Twitter City Mod built'
        }
    }
}