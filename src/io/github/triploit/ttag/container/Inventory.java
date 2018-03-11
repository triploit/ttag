package io.github.triploit.ttag.container;

import io.github.triploit.ttag.Item;
import io.github.triploit.ttag.Runtime;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Inventory implements Serializable
{
    public List<Item> items = new ArrayList<Item>();

    public Item getItemByName(String name)
    {
        for (Item i : items)
        {
            if (i.name.equalsName(name))
            {
                return i;
            }
        }

        return null;
    }

    public Item getItemByID(int id)
    {
        for (Item i : items)
        {
            if (i.id == id)
            {
                return i;
            }
        }

        return null;
    }

    public boolean existsItemByName(String name)
    {
        for (Item i : items)
        {
            if (i.name.equalsName(name))
            {
                return true;
            }
        }

        return false;
    }

    public void collectItemFromGame(int id)
    {
        if (getFilledSize() > Runtime.game.player.inventory_size)
        {
            System.out.println("Du hast schon zu viel Platz belegt ("+getFilledSize()+"/"+Runtime.game.player.inventory_size+"),\ndas Item, welches du nehmen willst ist zu groß ("+Runtime.game.getItemByID(id).size+")!");
            return;
        }
        items.add(Runtime.game.getItemByID(id));
        Runtime.game.removeItem(id);
    }

    public int getFilledSize()
    {
        int c = 0;
        for (Item i : items)
        {
            c += i.size;
        }
        return c;
    }

    public boolean existsItemByID(int id)
    {
        for (Item i : items)
        {
            if (i.id == id)
            {
                return true;
            }
        }

        return false;
    }

    public void removeItemByName(String name)
    {
        for (int i = 0; i < items.size(); i++)
        {
            if (items.get(i).name.equalsName(name)) items.remove(i);
        }
    }

    public void removeItemByID(int id)
    {
        for (int i = 0; i < items.size(); i++)
        {
            if (items.get(i).id == id) items.remove(i);
        }
    }
}
