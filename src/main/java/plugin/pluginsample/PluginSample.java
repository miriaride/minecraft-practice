package plugin.pluginsample;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.xml.crypto.Data;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.World;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.plugin.java.JavaPlugin;

public final class PluginSample extends JavaPlugin implements Listener {

  private int count;

  @Override
  public void onEnable() {
    Bukkit.getPluginManager().registerEvents(this, this);

  }


  /**
   * プレイヤーがスニークを開始/終了する際に起動されるイベントハンドラ。
   *
   * @param e イベント
   */
  @EventHandler
  public void onPlayerToggleSneak(PlayerToggleSneakEvent e) throws IOException {
    // イベント発生時のプレイヤーやワールドなどの情報を変数に持つ。
    Player player = e.getPlayer();
    World world = player.getWorld();

    // BigInteger型の val を定義
    BigInteger val = new BigInteger(String.valueOf(count));
    System.out.println("今::" + count);

    List<Color> colorList = List.of(Color.AQUA, Color.LIME, Color.ORANGE, Color.WHITE);

    if (val.isProbablePrime(10)) {

      for (Color color : colorList) {
        // 花火オブジェクトをプレイヤーのロケーション地点に対して出現させる。
        Firework firework = world.spawn(player.getLocation(), Firework.class);

        // 花火オブジェクトが持つメタ情報を取得。
        FireworkMeta fireworkMeta = firework.getFireworkMeta();

        // メタ情報に対して設定を追加したり、値の上書きを行う。
        // 今回は青色で星型の花火を打ち上げる。
        fireworkMeta.addEffect(
          FireworkEffect.builder()
            .withColor(color)
            .with(Type.BURST)
            .with(Type.CREEPER)
            .withTrail()
            .withFlicker()
            .build());
        fireworkMeta.setPower(2 * 2 + (2 + 2) % 3);

        // 追加した情報で再設定する。
        firework.setFireworkMeta(fireworkMeta);

        System.out.println("今::" + val + " は素数です");

        Path path = Path.of("firework.txt");
        Files.writeString(path, "たーまや");
        player.sendMessage(Files.readString(path));

      }
    }
    count++;

    // BigInteger側の val に対してnextProbablePrimeメソッドを使用
    System.out.println("次::" + val.nextProbablePrime()); // 1より大きい素数の２が出力されます。


  }

  @EventHandler
  public void onPlayerBetEnter(PlayerBedEnterEvent e) {
    Player player = e.getPlayer();
    ItemStack[] itemStack = player.getInventory().getContents();
    Arrays.stream(itemStack)
      .filter
        (item -> !Objects.isNull(item) && item.getMaxStackSize() == 64 && item.getAmount() < 64)
      .forEach(item -> item.setAmount(0));
    player.getInventory().setContents(itemStack);
    

  }


}
