package io.github.triploit.ttag;

import io.github.triploit.ttag.container.Name;
import io.github.triploit.ttag.container.RoomAttribut;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.*;
import java.util.Map;

public class Room implements Serializable // Siehe für eine bessere Dokumentation "Item.java" - ähnliches/gleiches Prinzip
{
    public int north = -1, south = -1, east = -1, west = -1; // Türen, auf die der Raum verweist. So später Türen möglich
    public Name name = new Name();
    public int id;
    public String v_name;
    public RoomAttribut attributes = new RoomAttribut(); // Attribute speziell für Räume
    public String see_dark;
    public String see_light;
    public int door;

    public static void init(Map d)
    {
        String lroom = "???";

        try
        {
            Map rooms = (Map) d.get("rooms");

            if (rooms == null && Runtime.warning)
            {
                Runtime.printWarning("(FATAL) keine raeume angegeben. ueberspringe.");
                return;
            }

            for (Object room : rooms.keySet().toArray())
            {
                Map tm = (Map) rooms.get((String) room);
                lroom = (String) room;

                if (tm != null)
                {
                    Room toadd = new Room();

                    toadd.v_name = Runtime.setString((String) room, "unmögliche warnung! sollte diese warnung auftreten, bitte kontaktieren sie umgehend den entwickler (WID: 1)!");
                    toadd.id = Runtime.setInt(tm.get("id"), "room "+lroom+": id wurde nicht gesetzt.");
                    toadd.see_light = Runtime.setString(tm.get("see_light"), "room "+lroom+": see_light wurde nicht gesetzt.");
                    toadd.door = Runtime.setInt(tm.get("door"), "room "+lroom+": door wurde nicht gesetzt.");
                    toadd.see_dark = Runtime.setString(tm.get("see_dark"), "room "+lroom+": see_dark wurde nicht gesetzt.");

                    if (tm.get("attributes") != null && ((Map) tm.get("attributes")) != null)
                    {
                        toadd.attributes.cold = Runtime.setBool(((Map) tm.get("attributes")).get("cold"), "room "+lroom+": attribut cold nicht gesetzt.");
                        toadd.attributes.hot = Runtime.setBool(((Map) tm.get("attributes")).get("hot"), "room "+lroom+": attribut hot nicht gesetzt.");
                        toadd.attributes.dark = Runtime.setBool(((Map) tm.get("attributes")).get("dark"), "room "+lroom+": attribut dark nicht gesetzt.");
                        toadd.attributes.inside = Runtime.setBool(((Map) tm.get("attributes")).get("inside"), "room "+lroom+": attribut inside nicht gesetzt.");
                    }

                    if (tm.get("name") != null && ((Map) tm.get("name")) != null)
                    {
                        toadd.name.light = Runtime.setString(((Map) tm.get("name")).get("light"), "room "+lroom+": der name \"name: light\" wurde nicht gesetzt.");
                        toadd.name.dark = Runtime.setString(((Map) tm.get("name")).get("dark"), "room "+lroom+": der name \"name: dark\" wurde nicht gesetzt.");
                        toadd.name.akkusativ = Runtime.setString(((Map) tm.get("name")).get("akkusativ"), "room "+lroom+": der name \"name: akkusativ\" wurde nicht gesetzt.");
                        toadd.name.alternative = Runtime.setString(((Map) tm.get("name")).get("alternative"), "room "+lroom+": der alternative \"name: dark\" wurde nicht gesetzt.");
                        toadd.name.dativ = Runtime.setString(((Map) tm.get("name")).get("dativ"), "room "+lroom+": der name \"name: dativ\" wurde nicht gesetzt.");
                        toadd.name.genitiv = Runtime.setString(((Map) tm.get("name")).get("genitiv"), "room "+lroom+": der name \"name: genitiv\" wurde nicht gesetzt.");

                        String s = Runtime.setString(((Map) tm.get("name")).get("genus"), "item " + lroom + ": der wert \"name: genus\" wurde nicht gesetzt.");

                        if (!s.equals("<NOT SET>") && !s.equals("f") && !s.equals("m") && !s.equals("n"))
                        {
                            System.out.println("error: falscher wert in "+lroom+": name: genus: darf nur f (weiblich), m (männlich) oder n (sächlich) als wert besitzen (hat aber \""+s+"\").");
                            System.exit(1);
                        }
                    }

                    if (tm.get("north") != null &&
                            (((String) tm.get("north")).equals(toadd.v_name) ||
                            ((String) tm.get("north")).equals(toadd.id+"")))
                    {
                        toadd.north = toadd.id;
                    }

                    if (tm.get("south") != null &&
                            (((String) tm.get("south")).equals(toadd.v_name) ||
                            ((String) tm.get("south")).equals(toadd.id+"")))
                    {
                        toadd.south = toadd.id;
                    }

                    if (tm.get("east") != null &&
                            (((String) tm.get("east")).equals(toadd.v_name) ||
                            ((String) tm.get("east")).equals(toadd.id+"")))
                    {
                        toadd.east = toadd.id;

                    }

                    if (tm.get("west") != null &&
                            (((String) tm.get("west")).equals(toadd.v_name) ||
                            ((String) tm.get("west")).equals(toadd.id+"")))
                    {
                        toadd.west = toadd.id;

                    }
                    
                    try
                    {
                        if (tm.get("north") != null && Runtime.game.idIsDefined(Integer.parseInt((String)tm.get("north"))))
                            toadd.north = Runtime.setInt(tm.get("north"), "room "+lroom+": north wurde nicht gesetzt.");
                        else if (tm.get("north") != null && !Runtime.game.idIsDefined(Integer.parseInt((String)tm.get("north"))))
                        {
                            toadd.north = Runtime.setInt(tm.get("north"), "room "+lroom+": north wurde nicht gesetzt.");
                            Runtime.addInitErrorIDs(Integer.parseInt((String) tm.get("north")), "error: raum "+toadd.v_name+": richtung norden konnte nicht gesetzt werden, da die id "+(String) tm.get("north")+" nicht definiert wurde.");
                        }

                        if (tm.get("south") != null && Runtime.game.idIsDefined(Integer.parseInt((String)tm.get("south"))))
                            toadd.south = Runtime.setInt(tm.get("south"), "room "+lroom+": south wurde nicht gesetzt.");
                        else if (tm.get("south") != null && !Runtime.game.idIsDefined(Integer.parseInt((String)tm.get("south"))))
                        {
                            toadd.south = Runtime.setInt(tm.get("south"), "room "+lroom+": south wurde nicht gesetzt.");
                            Runtime.addInitErrorIDs(Integer.parseInt((String) tm.get("south")), "error: raum "+toadd.v_name+": richtung sueden konnte nicht gesetzt werden, da die id "+(String) tm.get("south")+" nicht definiert wurde.");
                        }

                        if (tm.get("west") != null && Runtime.game.idIsDefined(Integer.parseInt((String)tm.get("west"))))
                            toadd.west = Runtime.setInt(tm.get("west"), "room "+lroom+": west wurde nicht gesetzt.");
                        else if (tm.get("west") != null && !Runtime.game.idIsDefined(Integer.parseInt((String)tm.get("west"))))
                        {
                            toadd.west = Runtime.setInt(tm.get("west"), "room "+lroom+": west wurde nicht gesetzt.");
                            Runtime.addInitErrorIDs(Integer.parseInt((String) tm.get("west")), "error: raum "+toadd.v_name+": richtung westen konnte nicht gesetzt werden, da die id "+(String) tm.get("west")+" nicht definiert wurde.");
                        }

                        if (tm.get("east") != null && Runtime.game.idIsDefined(Integer.parseInt((String)tm.get("east"))))
                            toadd.east = Runtime.setInt(tm.get("east"), "room "+lroom+": east wurde nicht gesetzt.");
                        else if (tm.get("east") != null && !Runtime.game.idIsDefined(Integer.parseInt((String)tm.get("east"))))
                        {
                            toadd.east = Runtime.setInt(tm.get("east"), "room "+lroom+": east wurde nicht gesetzt.");
                            Runtime.addInitErrorIDs(Integer.parseInt((String) tm.get("east")), "error: raum "+toadd.v_name+": richtung osten konnte nicht gesetzt werden, da die id "+(String) tm.get("east")+" nicht definiert wurde.");
                        }
                    }
                    catch (NumberFormatException ex)
                    {
                        ex.printStackTrace();
                        System.out.println("error: raum "+toadd.v_name+": richtungen ueberpruefen! es muessen ids verwendet werden.");
                        System.exit(1);
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

                    Runtime.game.rooms.add(toadd);
                }
            }
        }
        catch (Exception ex) {
            System.out.println("error: konnte den raum \""+lroom+"\" nicht abrufen! fehlt vielleicht etwasetwas oder wurde falsch eingetragen?");
            ex.printStackTrace();
            System.exit(1);
        }
    }
}
