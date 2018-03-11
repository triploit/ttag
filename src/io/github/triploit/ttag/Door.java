package io.github.triploit.ttag;

import io.github.triploit.ttag.container.Name;
import io.github.triploit.ttag.container.RoomAttribut;

import java.io.Serializable;
import java.util.Map;

public class Door implements Serializable // Siehe für bessere Dokumentation Item.java - ähnliches bis gleiches Prinzip
{
    public String v_name;
    public int id, key_id, room;
    public boolean dir_north, dir_south, dir_west, dir_east;
    public String des_north, des_south, des_west, des_east;

    public static void init(Map d)
    {
        String ldoor = "???";

        try
        {
            Map doors = (Map) d.get("doors");

            if (doors == null && Runtime.warning) // Schauen ob keine Türen angegeben wurden und ob man Warnungen ausgeben darf
            {
                Runtime.printWarning("(FATAL) keine tueren angegeben. ueberspringe.");
                return;
            }

            for (Object door : doors.keySet().toArray())
            {
                Map tm = (Map) doors.get((String) door);
                ldoor = (String) door;

                if (tm != null)
                {
                    // Mit Runtime.setXXX(Wert, WarnungsNachricht) kann man einen Wert setzen, ohne Vorher überprüfen zu müssen, ob dieser in einer Map definiert ist. Existiert dieser in der Map nicht, wird die Warnung ausgegeben

                    Door toadd = new Door();

                    toadd.v_name = Runtime.setString((String) door, "unmögliche warnung! sollte diese warnung auftreten, bitte kontaktieren sie umgehend den entwickler (WID: 1)!");
                    toadd.id = Runtime.setInt(tm.get("id"), "door "+ldoor+": id wurde nicht gesetzt.");
                    toadd.key_id = Runtime.setInt(tm.get("key_id"), "door "+ldoor+": key_id wurde nicht gesetzt.");
                    toadd.room = Runtime.setInt(tm.get("room"), "door "+ldoor+": room wurde nicht gesetzt.");

                    if (tm.get("description") == null)
                    {
                        Runtime.printWarning("door: "+ldoor+": description nicht gesetzt!");
                    }
                    else
                    {
                        toadd.des_east = Runtime.setString(((Map) tm.get("description")).get("east"), "door "+ldoor+": description: east nicht gesetzt!");
                        toadd.des_north = Runtime.setString(((Map) tm.get("description")).get("north"), "door "+ldoor+": description: north nicht gesetzt!");
                        toadd.des_west = Runtime.setString(((Map) tm.get("description")).get("west"), "door "+ldoor+": description: west nicht gesetzt!");
                        toadd.des_south = Runtime.setString(((Map) tm.get("description")).get("south"), "door "+ldoor+": description: south nicht gesetzt!");
                    }

                    if (tm.get("directions") == null)
                    {
                        Runtime.printWarning("door: "+ldoor+": directions nicht gesetzt!");
                    }
                    else
                    {
                        toadd.dir_east = Runtime.setBool(((Map) tm.get("directions")).get("east"), "door "+ldoor+": direction: east nicht gesetzt!");
                        toadd.dir_north = Runtime.setBool(((Map) tm.get("directions")).get("north"), "door "+ldoor+": direction: north nicht gesetzt!");
                        toadd.dir_west = Runtime.setBool(((Map) tm.get("directions")).get("west"), "door "+ldoor+": direction: west nicht gesetzt!");
                        toadd.dir_south = Runtime.setBool(((Map) tm.get("directions")).get("south"), "door "+ldoor+": direction: south nicht gesetzt!");
                    }

                    if (Runtime.game.nameIsDefined(toadd.v_name))
                    {
                        System.out.println("error: (feld-)name von "+toadd.v_name+" ist schon benutzt. bitte aendere diesen (feld-)namen.");
                        System.exit(1);
                    }

                    if (Runtime.game.idIsDefined(toadd.id))
                    {
                        System.out.println("error: id von "+toadd.v_name+" ist schon benutzt. bitte aendere diese id.");
                        System.exit(1);
                    }

                    Runtime.game.doors.add(toadd);
                }
            }
        }
        catch (Exception ex) {
            System.out.println("error: konnte den raum \""+ldoor+"\" nicht abrufen! fehlt vielleicht etwasetwas oder wurde falsch eingetragen?");
            ex.printStackTrace();
            System.exit(1);
        }
    }
}
