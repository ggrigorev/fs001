/*

package:gg.vivado.boards.board

<ip>
  <simplex>
    <name presence="REQUIRED" value="NMTOKEN" />
    <vendor presence="IMPLIED" value="NMTOKEN" />
    <library presence="IMPLIED" value="NMTOKEN" />
    <version presence="IMPLIED" value="CDATA" />
    <ip_interface presence="IMPLIED" value="NMTOKEN" />
  </simplex>
  <complex>
    <associated_board_interfaces array="associated_board_interfaces" />
  </complex>
</ip>

    import:java.util = ArrayList
    import:gg.base.xml.sch
    import:static gg.vivado.boards.board.L_Board
    import:static gg.vivado.boards.board.U_Board
    class:Ip = extends Schema
    String:name
    String:vendor
    String:library
    String:version
    String:ip_interface
    ArrayList<AssociatedBoardInterfaces>:associated_board_interfaces = new ArrayList<>()

*/

package gg.vivado.boards.board;

import java.util.ArrayList;
import gg.base.xml.sch.*;
import static gg.vivado.boards.board.L_Board.*;
import static gg.vivado.boards.board.U_Board.*;

public class Ip extends Schema {

public String name = null;
public String vendor = null;
public String library = null;
public String version = null;
public String ip_interface = null;
public ArrayList<AssociatedBoardInterfaces> associated_board_interfaces = new ArrayList<>();

}

