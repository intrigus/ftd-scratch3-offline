package ftd.block;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonTypeIdResolver;

import ftd.field.ScratchField;
import ftd.util.RelationShip;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "opcode")
@JsonTypeIdResolver(ScratchBlockResolver.class)
@JsonIgnoreProperties({ "x", "y" })
public abstract class ScratchBlock implements RelationShip {
	@JsonProperty(value = "opcode")
	public String opcode;
	@JsonProperty(value = "next")
	private String next_;
	public ScratchBlock next;
	@JsonProperty(value = "parent")
	private String parent_;
	public ScratchBlock parent;
	public boolean shadow;
	public boolean topLevel;

	@JsonProperty(value = "fields")
	public ScratchField fields;

	// @JsonProperty(value = "inputs")
	// public Map<String, ScratchValue> inputs;

	public void updateRelations(Map<String, ScratchBlock> blocks) {
		this.next = blocks.get(next_);
		this.parent = blocks.get(parent_);
		if (fields != null) {
			fields.updateRelations(blocks);
		}
		updateOtherRelations(blocks);
	}

	protected abstract void updateOtherRelations(Map<String, ScratchBlock> blocks);

	protected String beginGen() {
		return "";
	}

	protected String afterGen() {
		String code = "";
		if (this.next != null) {
			code += next.generateCode();
		}
		return code;
	}

	public String generateCode() {
		return beginGen() + gen() + afterGen();
	}

	protected abstract String gen();

	@Override
	public String toString() {
		return "ScratchBlock [opcode=" + opcode + ", next_=" + next_ + ", parent_=" + parent_ + ", shadow=" + shadow
				+ ", topLevel=" + topLevel + ", fields=" + fields + "]";
	}

}
