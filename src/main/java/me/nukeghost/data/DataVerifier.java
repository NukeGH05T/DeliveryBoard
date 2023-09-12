package me.nukeghost.data;

import static me.nukeghost.DeliveryBoard.plugin;

public class DataVerifier {
    String id1 = "4291";
    String id2 = "110579";

    public boolean verifyData() {

        if (plugin.getConfig().getString("api").equalsIgnoreCase("4291-110579-nuke")) {
            System.out.println("User: " + "%%__USER__%%");
            System.out.println("Username: " + "%%__USERNAME__%%");
            System.out.println("Resource: " + "%%__RESOURCE__%%");
            System.out.println("Nonce: " + "%%__NONCE__%%");
            System.out.println("R. Version: " + "%%__RESOURCE_VERSION__%%");
            System.out.println("Upload: " + "%%__UPLOAD__%%");
            System.out.println("Verify Token: " + "%%__VERIFY_TOKEN__%%");
            System.out.println("Agent: " + "%%__AGENT__%%");
            System.out.println("TimeStamp: " + "%%__TIMESTAMP__%%");
            System.out.println("License: " + "%%__LICENSE__%%");
        }

        if (!"%%__POLYMART__%%".equalsIgnoreCase("1")) return true;

        return true;
    }
}
