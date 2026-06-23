/*

package:gg.vivado.boards.board

<preferred_ips>
  <complex>
    <preferred_ip array="preferred_ip" />
  </complex>
</preferred_ips>

    import:java.util = ArrayList
    import:gg.base.xml.sch
    import:static gg.vivado.boards.board.L_Board
    import:static gg.vivado.boards.board.U_Board
    class:PreferredIps = extends Schema
    ArrayList<PreferredIp>:preferred_ip = new ArrayList<>()

*/

package gg.vivado.boards.board;

import java.util.ArrayList;
import gg.base.xml.sch.*;
import static gg.vivado.boards.board.L_Board.*;
import static gg.vivado.boards.board.U_Board.*;

public class PreferredIps extends Schema {

public ArrayList<PreferredIp> preferred_ip = new ArrayList<>();

}

