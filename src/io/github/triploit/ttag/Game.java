package io.github.triploit.ttag;

import java.io.Serializable;
import java.util.*;

public class Game implements Serializable
{
    public List<Monster> monsters = new ArrayList<Monster>();
    public List<Item> items = new ArrayList<Item>();
    public List<Room> rooms = new ArrayList<Room>();
    public List<Door> doors = new ArrayList<Door>();
    public Player player = new Player();

    public String name;

    public Player getPlayer()
    {
        return player;
    }

    public void setPlayerPosition(int pos)
    {
        player.position = pos;
    }

    public Item getItem(String name)
    {
        for (Item d : items)
        {
            if (name.equals(d.v_name))
            {
                return d;
            }
        }

        return null;
    }

    public Item getItemByID(int id)
    {
        for (Item d : items)
        {
            if (d.id == id)
            {
                return d;
            }
        }

        return null;
    }

    public Item getItemByName(String name)
    {
        for (Item d : items)
        {
            if (d.name.equalsName(name))
            {
                return d;
            }
        }

        return null;
    }

    public Monster getMonster(String name)
    {
        for (Monster d : monsters)
        {
            if (name.equals(d.v_name))
            {
                return d;
            }
        }

        return null;
    }

    public Monster getMonsterByID(int id)
    {
        for (Monster d : monsters)
        {
            if (d.id == id)
            {
                return d;
            }
        }

        return null;
    }

    public Room getRoom(String name)
    {
        for (Room d : rooms)
        {
            if (name.equals(d.v_name))
            {
                return d;
            }
        }

        return null;
    }

    public Room getRoomByName(String name)
    {
        for (Room d : rooms)
        {
            if (d.name.equalsName(name))
            {
                return d;
            }
        }

        return null;
    }

    public boolean existsDoor(int id)
    {
        for (Door d : doors)
        {
            if (d.id == id)
            {
                return true;
            }
        }

        return false;
    }

    public Door getDoor(int id)
    {
        if (!existsDoor(id)) return null;

        for (Door d : doors)
        {
            if (d.id == id)
            {
                return d;
            }
        }

        return null;
    }

    public String getDirectionDescription(int door, String direction)
    {
        Door d = getDoor(door);
        if (d == null) return null;

        switch (direction)
        {
            case "south":
                return d.des_south;
            case "west":
                return d.des_west;
            case "north":
                return d.des_north;
            case "east":
                return d.des_east;
            default:
                return null;
        }
    }

    public boolean isDirectionClosed(int door, String direction)
    {
        Door d = getDoor(door);
        if (d == null) return false;

        switch (direction)
        {
            case "south":
                return !d.dir_south;
            case "west":
                return !d.dir_west;
            case "north":
                return !d.dir_north;
            case "east":
                return !d.dir_east;
            default:
                return false;
        }
    }

    public Room getRoomByID(int id)
    {
        for (Room d : rooms)
        {
            if (d.id == id)
            {
                return d;
            }
        }

        return null;
    }

    public boolean idIsDefined(int id)
    {
        for (Room d : rooms)
        {
            if (d.id == id) return true;
        }
        for (Item d : items)
        {
            if (d.id == id) return true;
        }
        for (Monster d : monsters)
        {
            if (d.id == id) return true;
        }
        return false;
    }

    public boolean nameIsDefined(String name)
    {
        if (name == null)
        {
            System.out.println("error: v_name des objektes ist null!");
            return true;
        }

        for (Room d : rooms)
        {
            if (name.equals(d.v_name)) return true;
        }
        for (Item d : items)
        {
            if (name.equals(d.v_name)) return true;
        }

        for (Monster d : monsters)
        {
            if (name.equals(d.v_name))
                return true;
        }

        return false;
    }

    public static String[] sortStrArray(String[] array)
    {
        //sort arrays by length before returning
        Arrays.sort(array, new Comparator<String>()
        {
            @Override
            public int compare(String a, String b)
            {
                return Integer.compare(a.length(), b.length());//specifying compare type that is compare with length
            }
        });
        return array;
    }

    public Integer[] findNameIn(String s)
    {
        for (Item d : items)
        {
            if (player.position == d.position)
            {
                List<String> l = new ArrayList<>();
                l.add(d.name.alternative);
                l.add(d.name.akkusativ);
                l.add(d.name.genitiv);
                l.add(d.name.dativ);
                l.add(d.name.name);

                String[] stockArr = new String[l.size()];
                stockArr = l.toArray(stockArr);

                l = Arrays.asList(sortStrArray(stockArr));

                for (int i = l.size() - 1; i >= 0; i--)
                {
                    if (s.contains(l.get(i)))
                    {
                        return new Integer[]{s.indexOf(l.get(i)), l.get(i).length()};
                    }
                }
            }
        }

        return null;
    }

    public boolean existsItem(String s)
    {
        for (Item i : items)
        {
            if (i.name.equalsName(s) && i.position == player.position)
            {
                return true;
            }
        }
        return false;
    }

    public boolean existsRoom(String s)
    {
        for (Room i : rooms)
        {
            if (i.name.equalsName(s))
            {
                return true;
            }
        }
        return false;
    }

    public void setItem(String name, Item i)
    {

    }

    public List<Item> getItemsAtPosition(int pos)
    {
        List<Item> is = new ArrayList<>();

        for (Item i : items)
        {
            if (i.position == pos)
            {
                is.add(i);
            }
        }

        return is;
    }

    public void removeItem(int id)
    {
        for (int i = 0; i < items.size(); i++)
        {
            if (items.get(i).id == id)
                items.remove(i);
        }
    }

    public void addItem(Item i) // Item zum Spiel hinzufügen
    {
        items.add(i);
    }

    public void addItemFromPlayer(int id) // Item aus Players Inventar holen
    {
        Item i = Runtime.game.player.inventory.getItemByID(id);
        i.position = Runtime.game.player.position;

        addItem(i);
        Runtime.game.player.inventory.removeItemByID(id);
    }
}
