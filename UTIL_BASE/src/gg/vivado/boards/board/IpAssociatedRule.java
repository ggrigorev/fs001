/*

package:gg.vivado.boards.board

<ip_associated_rule>
  <simplex>
    <name presence="REQUIRED" value="NMTOKEN" />
  </simplex>
  <complex>
    <ip array="ip" />
  </complex>
</ip_associated_rule>

    import:java.util = ArrayList
    import:gg.base.xml.sch
    import:static gg.vivado.boards.board.L_Board
    import:static gg.vivado.boards.board.U_Board
    class:IpAssociatedRule = extends Schema
    String:name
    ArrayList<Ip>:ip = new ArrayList<>()

*/

package gg.vivado.boards.board;

import java.util.ArrayList;
import gg.base.xml.sch.*;
import static gg.vivado.boards.board.L_Board.*;
import static gg.vivado.boards.board.U_Board.*;

public class IpAssociatedRule extends Schema {

public String name = null;
public ArrayList<Ip> ip = new ArrayList<>();

}

