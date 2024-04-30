package net.smileycorp.elites.common.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.PacketListener;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;
import net.smileycorp.atlas.api.network.AbstractMessage;
import net.smileycorp.elites.client.ClientHandler;
import net.smileycorp.elites.common.affixes.Affix;
import net.smileycorp.elites.common.affixes.Affixes;
import org.checkerframework.checker.units.qual.A;

import java.util.Optional;

public class SyncAffixMessage extends AbstractMessage {
    
    private Affix affix;
    private int id;
    
    public SyncAffixMessage(){}
    
    public SyncAffixMessage(Affix affix, LivingEntity entity) {
        this.affix = affix;
        this.id = entity.getId();
    }
    
    @Override
    public void read(FriendlyByteBuf buf) {
        Optional<Affix> optional = Affixes.getAffix(buf.readResourceLocation());
        if (optional.isPresent()) affix = optional.get();
        id = buf.readInt();
    }
    
    @Override
    public void write(FriendlyByteBuf buf) {
        buf.writeResourceLocation(affix.getRegistryName());
        buf.writeInt(id);
    }
    
    @Override
    public void handle(PacketListener handler) {}
    
    @Override
    public void process(NetworkEvent.Context ctx) {
        ctx.enqueueWork(() -> DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> () -> ClientHandler.syncAffix(affix, id)));
        ctx.setPacketHandled(true);
    }
    
}
