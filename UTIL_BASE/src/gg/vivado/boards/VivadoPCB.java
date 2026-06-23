package gg.vivado.boards;

import gg.base.text.TMap;
import gg.vivado.boards.board.Board;
import gg.vivado.boards.part0_pins.PartInfo;
import gg.vivado.boards.preset.IpPresets;

public class VivadoPCB {

	public Board			board;
	public IpPresets		presets;
	public TMap<PartInfo>	parts = new TMap<>();

}
