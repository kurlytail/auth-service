pipeline {

    agent none
    
    parameters {
        string(defaultValue: "0.0", description: 'Build version prefix', name: 'BUILD_VERSION_PREFIX')
        string(defaultValue: "", description: 'Build number offset', name: 'BUILDS_OFFSET')
    }

    stages {
        stage('Prepare env') {
            agent {
                label 'master'
            }
            
            steps {
                script {
                    loadLibrary()
                    env['MAVEN_VERSION_NUMBER'] = getMavenVersion 'kurlytail/auth-service/master', params.BUILD_VERSION_PREFIX, params.BUILDS_OFFSET
                	env.PATH = env.PATH + ':/usr/local/bin'
                	currentBuild.displayName = env['MAVEN_VERSION_NUMBER']
                }
            }
        }
        
        stage ('Build') {
            agent {
                label 'mvn'
            }
            
            steps {
                sh 'rm -rf *'
     
                checkout scm
                withMaven (options: [
                	dependenciesFingerprintPublisher(disabled: false),
                	concordionPublisher(disabled: false),
                	artifactsPublisher(disabled: true),
                	pipelineGraphPublisher(disabled: false, lifecycleThreshold: install)
                ]) {
		            sh 'mvn --batch-mode -s settings.xml release:update-versions -DautoVersionSubmodules=true -DdevelopmentVersion=$MAVEN_VERSION_NUMBER'
		            sh 'mvn --batch-mode -s settings.xml clean deploy'
		            sh 'mvn --batch-mode -s settings.xml dockerfile:build'
		        }
		        
	            sh "docker stop auth-service || true"
	            sh "docker rm auth-service || true"
		        sh '''docker run --restart unless-stopped -d -p 10002:80 --dns \$(docker inspect -f \'{{.NetworkSettings.IPAddress}}\' dns) --dns-search brainspeedtech.com --name auth-service --hostname auth-service.brainspeedtech.com brainspeedtech/auth-service:\$MAVEN_VERSION_NUMBER'''
		        sh 'cp nginx.conf /usr/local/etc/nginx/servers/auth-service.conf'
		        sh 'brew services restart nginx'
            }
        }
    }
}

