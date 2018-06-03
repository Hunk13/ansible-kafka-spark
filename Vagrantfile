VAGRANTFILE_API_VERSION = "2"

Vagrant.configure(VAGRANTFILE_API_VERSION) do |config|
  config.vm.box = "ubuntu/trusty64"

  config.vm.synced_folder "pub/", "/vagrant"

  config.vm.network "forwarded_port", guest: 8080, host: 8080

  config.vm.provider "virtualbox" do |v|
    v.memory = 2048
  end

  config.vm.define "dev01" do |machine|
    machine.vm.hostname = "dev01"
  end

  config.vm.provision "ansible" do |ansible|
    ansible.groups = {
      "kafka.development" => ["dev01"]
    }
    ansible.playbook = "kafka.yml"
  end
end
