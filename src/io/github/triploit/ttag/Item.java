package io.github.triploit.ttag;

import io.github.triploit.ttag.container.ItemAttribut;
import io.github.triploit.ttag.container.Name;

import java.io.Serializable;
import java.util.Map;

public class Item implements Serializable
{
    public int id; // Die ID
    public String v_name; // Der Variablenname
    public int size; // Die größe des Items

    public Name name = new Name(); // Der Name des Items
    public ItemAttribut attributes = new ItemAttribut(); // Attribute des Items
    public String description; // Beschreibung des Items

    public String see_dark; // Beschreibung, wenn es hell ist
    public String see_light; // Beschreibung, wenn es dunkel ist
    public int position; // Wo das Item liegt./Position

    public Item(int id, String name)
    {
        this.id = id;
        this.name.alternative = name;
    }

    public Item() {}

    public static void init(Map d)
    {
        String litem = "???";

        try
        {
            Map items = (Map) d.get("items");

            if (items == null && Runtime.warning)
            {
                Runtime.printWarning("(FATAL) keine items angegeben. ueberspringe.");
                return;
            }

            for (Object item : items.keySet().toArray()) // Durch alle Items in der Map iterieren
            {
                Item toadd = new Item(); // Das Item, das hinzugefügt werden soll
                Map tm = (Map) items.get((String) item); // Die Map/generiert aus der YAML-Datei mit allen Items
                litem = (String) item; // Name des aktuellen Items

                toadd.v_name = Runtime.setString((String) item, "unmögliche warnung! sollte diese warnung auftreten, bitte kontaktieren sie umgehend den entwickler (WID: 1)!");

                if (tm.get("name") != null && (Map) tm.get("name") != null) // Schauen ob Attribut "name" existiert
                {
                    // Mit Runtime.setXXX(Wert, WarnungsNachricht) kann man einen Wert setzen, ohne Vorher überprüfen zu müssen, ob dieser in einer Map definiert ist. Existiert dieser in der Map nicht, wird die Warnung ausgegeben

                    toadd.name.light = Runtime.setString(((Map) tm.get("name")).get("light"), "item " + litem + ": der name \"name: light\" wurde nicht gesetzt."); // Wert von toadd.name.light aus Map holen
                    toadd.name.dark = Runtime.setString(((Map) tm.get("name")).get("dark"), "item " + litem + ": der name \"name: dark\" wurde nicht gesetzt.");
                    toadd.name.akkusativ = Runtime.setString(((Map) tm.get("name")).get("akkusativ"), "item " + litem + ": der name \"name: akkusativ\" wurde nicht gesetzt.");
                    toadd.name.alternative = Runtime.setString(((Map) tm.get("name")).get("alternative"), "item " + litem + ": der alternative \"name: dark\" wurde nicht gesetzt.");
                    toadd.name.dativ = Runtime.setString(((Map) tm.get("name")).get("dativ"), "item " + litem + ": der name \"name: dativ\" wurde nicht gesetzt.");
                    toadd.name.genitiv = Runtime.setString(((Map) tm.get("name")).get("genitiv"), "item " + litem + ": der name \"name: genitiv\" wurde nicht gesetzt.");
                    toadd.name.name = Runtime.setString(((Map) tm.get("name")).get("name"), "item " + litem + ": der name \"name: name\" wurde nicht gesetzt.");

                    String s = Runtime.setString(((Map) tm.get("name")).get("genus"), "item " + litem + ": der wert \"name: genusd\" wurde nicht gesetzt.");

                    if (!s.equals("<NOT SET>") && !s.equals("f") && !s.equals("m") && !s.equals("n"))
                    {
                        System.out.println("error: falscher wert in "+litem+": name: genus: darf nur f (weiblich), m (männlich) oder n (sächlich) als wert besitzen (hat aber \""+s+"\").");
                        System.exit(1);
                    }
                }

                toadd.id = Runtime.setInt(tm.get("id"), "item "+item+": die item id wurde nicht gesetzt.");
                toadd.size = Runtime.setInt(tm.get("size"), "item "+item+": die item größe (size) wurde nicht gesetzt.");
                toadd.position = Runtime.setInt(tm.get("position"), "item "+item+": die item position wurde nicht gesetzt.");
                toadd.see_light = Runtime.setString(tm.get("see_light"), "item "+item+": see_light wurde nicht gesetzt.");
                toadd.see_dark = Runtime.setString(tm.get("see_dark"), "item "+item+": see_dark wurde nicht gesetzt.");
                toadd.description = Runtime.setString(tm.get("description"), "item "+item+": description wurde nicht gesetzt.");

                if (tm.get("attributes") != null && (Map) tm.get("attributes") != null) // Schauen ob Attribut "attributes" existiert
                {
                    toadd.attributes.catchable = Runtime.setBool(((Map) tm.get("attributes")).get("catchable"), "item "+litem+": das attribut \"attribut: catchable\" wurde nicht gesetzt.");
                    toadd.attributes.key = Runtime.setBool(((Map) tm.get("attributes")).get("key"), "item "+litem+": das attribut \"attribut: key\" wurde nicht gesetzt.");
                    toadd.attributes.key_id = Runtime.setInt(((Map) tm.get("attributes")).get("key_id"), "item "+litem+": das attribut \"attribut: key_id\" wurde nicht gesetzt.");
                    toadd.attributes.light = Runtime.setBool(((Map) tm.get("attributes")).get("light"), "item "+litem+": das attribut \"attribut: light\" wurde nicht gesetzt.");
                    toadd.attributes.text = Runtime.setString(((Map) tm.get("attributes")).get("text"), "item "+litem+": das attribut \"attribut: text\" wurde nicht gesetzt.");
                    toadd.attributes.weapon = Runtime.setBool(((Map) tm.get("attributes")).get("weapon"), "item "+litem+": das attribut \"attribut: weapon\" wurde nicht gesetzt.");
                    toadd.attributes.activateable = Runtime.setBool(((Map) tm.get("attributes")).get("activateable"), "item "+litem+": das attribut \"attribut: activateable\" wurde nicht gesetzt.");
                    toadd.attributes.activated = Runtime.setBool(((Map) tm.get("attributes")).get("activated"), "item "+litem+": das attribut \"attribut: activated\" wurde nicht gesetzt.");
                    toadd.attributes.need_to_activate = Runtime.setListSTR(((Map) tm.get("attributes")).get("need_to_activate"), "item "+litem+": das attribut \"attribut: need_to_activate\" wurde nicht gesetzt.");
                    toadd.attributes.burnable = Runtime.setBool(((Map) tm.get("attributes")).get("burnable"), "item "+litem+": das attribut \"attribut: burnable\" wurde nicht gesetzt.");
                    toadd.attributes.fire_at_activate = Runtime.setBool(((Map) tm.get("attributes")).get("fire_at_activate"), "item "+litem+": das attribut \"attribut: fire_at_activate\" wurde nicht gesetzt.");
                    toadd.attributes.readable = Runtime.setBool(((Map) tm.get("attributes")).get("readable"), "item "+litem+": das attribut \"attribut: readable\" wurde nicht gesetzt.");
                }

                if (Runtime.game.nameIsDefined(toadd.v_name)) // Schauen ob der Variablenname des aktuellen Items schon benutzt wird
                {
                    System.out.println("error: (feld-)name von "+toadd.v_name+" ist schon benutzt. bitte aendere diesen (feld-)namen.");
                    System.exit(1);
                }

                if (Runtime.game.idIsDefined(toadd.id)) // Schauen ob die ID des aktuellen Items schon benutzt wird
                {
                    System.out.println("error: id von "+toadd.v_name+" ist schon benutzt. bitte aendere diese id.");
                    System.exit(1);
                }

                Runtime.game.items.add(toadd);
            }
        }
        catch (Exception ex)
        {
            if (litem == "???") return;

            System.out.println("error: konnte das item \""+litem+"\" nicht abrufen! fehlt vielleicht etwas vergessen oder wurde falsch eingetragen?");
            ex.printStackTrace();
            System.exit(1);
        }
    }
}
