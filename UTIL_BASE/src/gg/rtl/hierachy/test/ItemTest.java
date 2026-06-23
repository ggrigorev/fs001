package gg.rtl.hierachy.test;

import static gg.base.util.U_Print.*;
import static gg.hierachy.rtl.U_Item.*;

import gg.hierachy.rtl.*;

public class ItemTest {

	public static void main(String[] args) {
        Item item = new Item();
        item.name = "a";
        prn_("Item", item);
        Item itemClone = newClone(item);
        prn_("Item Clone", itemClone);

        Node node = newItem("A", Node.class);
        node.add(item);
        prn_("Node", node);
       
        Node nodeClone = newClone(node);
        prn_("Node Clone", nodeClone);

	}

}
