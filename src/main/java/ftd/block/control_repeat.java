package ftd.block;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

import ftd.ScratchValue;

public class control_repeat extends ScratchBlock {

	@JsonProperty(value = "inputs")
	private Input inputs;

	private static class Input {
		@JsonProperty(value = "SUBSTACK")
		public ScratchValue subStack;

		@JsonProperty(value = "TIMES")
		public ScratchValue times;
	}

	@Override
	protected String beginGen() {
		String expression = this.inputs.times.generateCode();
		//return "for(uint32_t i = 0; lt(i, (" + expression + ")); i++) {\n";
		return "for(uint32_t i = 0; i < 10; i++) {\n";
	}

	@Override
	protected String afterGen() {
		return super.afterGen() + "}\n";
	}

	@Override
	protected String gen() {
		return inputs.subStack.generateCode();
	}

	@Override
	protected void updateOtherRelations(Map<String, ScratchBlock> blocks) {
		this.inputs.subStack.updateRelations(blocks);
		this.inputs.times.updateRelations(blocks);
	}

}
