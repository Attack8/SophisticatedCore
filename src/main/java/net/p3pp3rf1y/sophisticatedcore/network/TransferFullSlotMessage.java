package net.p3pp3rf1y.sophisticatedcore.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;
import net.p3pp3rf1y.sophisticatedcore.common.gui.StorageContainerMenuBase;

import javax.annotation.Nullable;
import java.util.function.Supplier;

public class TransferFullSlotMessage {
	private final int slotId;

	public TransferFullSlotMessage(int slotId) {
		this.slotId = slotId;
	}

	public static void encode(TransferFullSlotMessage msg, FriendlyByteBuf packetBuffer) {
		packetBuffer.writeInt(msg.slotId);
	}

	public static TransferFullSlotMessage decode(FriendlyByteBuf packetBuffer) {
		return new TransferFullSlotMessage(packetBuffer.readInt());
	}

	public static void onMessage(TransferFullSlotMessage msg, Supplier<NetworkEvent.Context> contextSupplier) {
		NetworkEvent.Context context = contextSupplier.get();
		context.enqueueWork(() -> handleMessage(context.getSender(), msg));
		context.setPacketHandled(true);
	}

	private static void handleMessage(@Nullable ServerPlayer player, TransferFullSlotMessage msg) {
		if (player == null || !(player.containerMenu instanceof StorageContainerMenuBase<?> storageContainer)) {
			return;
		}
		Slot slot = storageContainer.getSlot(msg.slotId);
		ItemStack transferResult;
		do {
			transferResult = storageContainer.quickMoveStack(player, msg.slotId);
		} while (!transferResult.isEmpty() && ItemStack.isSame(slot.getItem(), transferResult));
	}
}
