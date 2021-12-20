#!groovy
println('------------------------------------------------------------------Import Job CD/Ansible')
def pipelineScript = new File('/var/jenkins_config/jobs/ansible-pipeline.groovy').getText("UTF-8")

pipelineJob('CD/Ansible') {
    description("Job ansible pipeline")
    parameters {
        stringParam {
            name('AWS_HOST')
            defaultValue('deploy@')
            description("AWS instance IP format :  user@AWS_IP")
            trim(false)
        }
        stringParam {
            name('ANSIBLE_PATH')
            defaultValue('/usr/share/ansible')
            description("ansible path in docker")
            trim(false)
        }
        stringParam {
            name('SSH_KEY_PATH')
            defaultValue('~/.ssh/id_rsa')
            description("ssh private key")
            trim(false)
        }
    }
    definition {
        cps {
            script(pipelineScript)
            sandbox()
        }
    }
}