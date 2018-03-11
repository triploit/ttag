package io.github.triploit.ttag;

import io.github.triploit.ttag.container.Name;

import java.io.Serializable;
import java.util.Map;

public class Monster implements Serializable // Bessere Dokumentation in Item.java - ähnliches/gleiches Prinzip
{
    public int strength; // Stärke des Monsters (Angriff pro Schlag)
    public int health; // Wie viele Leben das Monster hat
    public Name name = new Name();
    public int position; // Wo das Monster ist
    public int id; // ID des Monsters
    public String v_name; // Variablenname

    public static void init(Map d)
    {
        String lmonster = "???";

        try
        {
            Map monsters = (Map) d.get("monsters");

            if (monsters == null && Runtime.warning)
            {
                Runtime.printWarning("keine monster angegeben. ueberspringe.");
                return;
            }

            for (Object monster : monsters.keySet().toArray())
            {
                Map tm = (Map) monsters.get((String) monster);
                lmonster = (String) monster;

                if (tm != null)
                {
                    Monster toadd = new Monster();

                    toadd.v_name = (String) monster;
                    toadd.health = Runtime.setInt((tm.get("health")), "monster "+lmonster+": health wurde nicht gesetzt.");
                    toadd.strength = Runtime.setInt((tm.get("strength")), "monster "+lmonster+": strength wurde nicht gesetzt.");
                    toadd.position = Runtime.setInt((tm.get("position")), "monster "+lmonster+": position wurde nicht gesetzt.");
                    toadd.id = Runtime.setInt((tm.get("id")), "monster "+lmonster+": id wurde nicht gesetzt");

                    if (tm.get("name") != null && (Map) tm.get("name") != null)
                    {
                        toadd.name.light = Runtime.setString(((Map) tm.get("name")).get("light"), "monster "+lmonster+": der name \"name: light\" wurde nicht gesetzt.");
                        toadd.name.dark = Runtime.setString(((Map) tm.get("name")).get("dark"), "monster "+lmonster+": der name \"name: dark\" wurde nicht gesetzt.");
                        toadd.name.akkusativ = Runtime.setString(((Map) tm.get("name")).get("akkusativ"), "monster "+lmonster+": der name \"name: akkusativ\" wurde nicht gesetzt.");
                        toadd.name.alternative = Runtime.setString(((Map) tm.get("name")).get("alternative"), "monster "+lmonster+": der alternative \"name: dark\" wurde nicht gesetzt.");
                        toadd.name.dativ = Runtime.setString(((Map) tm.get("name")).get("dativ"), "monster "+lmonster+": der name \"name: dativ\" wurde nicht gesetzt.");
                        toadd.name.genitiv = Runtime.setString(((Map) tm.get("name")).get("genitiv"), "monster "+lmonster+": der name \"name: genitiv\" wurde nicht gesetzt.");

                        String s = Runtime.setString(((Map) tm.get("name")).get("genus"), "item " + lmonster + ": der wert \"name: genus\" wurde nicht gesetzt.");

                        if (!s.equals("<NOT SET>") && !s.equals("f") && !s.equals("m") && !s.equals("n"))
                        {
                            System.out.println("error: falscher wert in "+lmonster+": name: genus: darf nur f (weiblich), m (männlich) oder n (sächlich) als wert besitzen (hat aber \""+s+"\").");
                            System.exit(1);
                        }
                    }
                    else Runtime.printWarning("monster "+lmonster+": es wurde kein name gesetzt.");

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

                    Runtime.game.monsters.add(toadd);
                }
            }
        }
        catch (Exception ex)
        {
            System.out.println("error: konnte das monster \""+lmonster+"\" nicht abrufen! fehlt vielleicht etwas oder wurde falsch eingetragen?");
            //ex.printStackTrace();
            System.exit(1);
        }
    }
}
