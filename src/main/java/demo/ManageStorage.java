package demo;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.openstack4j.api.Builders;
import org.openstack4j.api.OSClient.OSClientV2;
import org.openstack4j.model.common.Payloads;
import org.openstack4j.model.storage.object.SwiftContainer;
import org.openstack4j.openstack.OSFactory;

public class ManageStorage {
	private static HashMap<String, String> usersMapping;
	
	public static void main(String[] args) throws IOException, InterruptedException {
	
		// Utility class
		Utility utils = new Utility();

		// Read users file
		usersMapping = utils.readConfigurationFile("config.properties");

		OSClientV2 os = OSFactory.builderV2().endpoint("https://ops.elastx.net:5000/v2.0")
				.credentials(usersMapping.get("openstack.username"), usersMapping.get("openstack.pwd"))
				.tenantName(usersMapping.get("openstack.tenantName")).authenticate();
		// Create a volume - Cinder
		os.blockStorage().volumes().create(Builders.volume().name("TestVolume4J")
				.description("Simple volume to store backups on").size(1).build());

		// Create a new container
		os.objectStorage().containers().create("testContainer");
		
		// Push an object
		String etag = os.objectStorage().objects().put("testContainer", "myObj", Payloads.create(utils.getObjFile()));
		System.out.format("Checksum value: %s\n", etag);
		
		List<? extends SwiftContainer> containers = os.objectStorage().containers().list();
		for (SwiftContainer swiftContainer : containers) {
			System.out.format("Swift container: %s", swiftContainer.getName());
		}
	}
}
