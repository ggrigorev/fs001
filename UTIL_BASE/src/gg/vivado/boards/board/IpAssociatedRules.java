/*

package:gg.vivado.boards.board

<ip_associated_rules>
  <complex>
    <ip_associated_rule />
  </complex>
</ip_associated_rules>

    import:java.util = ArrayList
    import:gg.base.xml.sch
    import:static gg.vivado.boards.board.L_Board
    import:static gg.vivado.boards.board.U_Board
    class:IpAssociatedRules = extends Schema
    IpAssociatedRule:ip_associated_rule

*/

package gg.vivado.boards.board;

import java.util.ArrayList;
import gg.base.xml.sch.*;
import static gg.vivado.boards.board.L_Board.*;
import static gg.vivado.boards.board.U_Board.*;

public class IpAssociatedRules extends Schema {

public IpAssociatedRule ip_associated_rule = null;

}

