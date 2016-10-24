package demo;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.openstack4j.api.Builders;
import org.openstack4j.api.OSClient.OSClientV2;
import org.openstack4j.model.compute.FloatingIP;
import org.openstack4j.model.compute.Server;
import org.openstack4j.model.compute.ServerCreate;
import org.openstack4j.openstack.OSFactory;

public class LaunchVM {
	private static HashMap<String, String> usersMapping;

	public static void main(String[] args) throws IOException, InterruptedException {
		// Utility class
		Utility utils = new Utility();

		// Read users file
		usersMapping = utils.readConfigurationFile("config.properties");

		OSClientV2 os = OSFactory.builderV2().endpoint("https://ops.elastx.net:5000/v2.0")
				.credentials(usersMapping.get("openstack.username"), usersMapping.get("openstack.pwd"))
				.tenantName(usersMapping.get("openstack.tenantName")).authenticate();

		// Create a Server Model Object
		ServerCreate vm = Builders.server().name("MyFirstVM").flavor("fdd59906-bfe9-48bf-ad01-2bd18bc85151")
				.image("5fe30545-f50a-4854-8105-8105c4f255b9").keypairName("demo-key").build();

		// Boot the Server
		Server server = os.compute().servers().boot(vm);
		
		// Print list of VMs
		System.out.println("### Available VMs ###");
		for (Server srv : os.compute().servers().list()) {
			System.out.format("Instance id: %s, Status: %s;Instance name: %s, Flavor id: %s \n", srv.getId(),
					srv.getStatus(), srv.getName(), srv.getFlavorId());
		}
		
		// Print list of allocated Floating IPs 
		System.out.println("### Floating IPs ###");
		List<? extends FloatingIP> fIps = os.compute().floatingIps().list();
		for (FloatingIP fIp : fIps) {
			System.out.println(fIp.getFloatingIpAddress());
		}
		
		// It is just a workaround. All activities should be orchestrated.
		Thread.sleep(5000); 
		
		// Assign a floating IP to VM
		os.compute().floatingIps().addFloatingIP(server, "192.121.20.164");
	}
}
