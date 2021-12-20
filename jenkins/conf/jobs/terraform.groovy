#!groovy
println('------------------------------------------------------------------Import Job CD/terraform')
def pipelineScript = new File('/var/jenkins_config/jobs/terraform-pipeline.groovy').getText("UTF-8")

pipelineJob('CD/terraform') {
    description("Job terraform")
    parameters {
        stringParam {
            name('TERRAFORM_PATH')
            defaultValue('/usr/share/terraform')
            description("terraform path in docker")
            trim(false)
        }
        stringParam {
            name('PUBLIC_SSH')
            defaultValue('')
            description("The ssh key, please et blackslash before each slash")
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