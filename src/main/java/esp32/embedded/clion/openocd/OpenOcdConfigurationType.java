package esp32.embedded.clion.openocd;

import com.intellij.execution.configurations.ConfigurationFactory;
import com.intellij.execution.configurations.RunConfiguration;
import com.intellij.execution.configurations.RunConfigurationSingletonPolicy;
import com.intellij.ide.ui.ProductIcons;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.IconLoader;
import com.intellij.openapi.util.NotNullLazyValue;
import com.jetbrains.cidr.cpp.execution.CMakeRunConfigurationType;
import javax.swing.Icon;
import org.jetbrains.annotations.NotNull;

/**
 * (c) elmot on 29.9.2017.
 */
public class OpenOcdConfigurationType extends CMakeRunConfigurationType {

    private static final String FACTORY_ID = "elmot.embedded.openocd.conf.factory";
    public static final String TYPE_ID = "elmot.embedded.openocd.conf.type";
    public static final NotNullLazyValue<Icon> ICON = NotNullLazyValue.lazy(() -> {
        final Icon icon = IconLoader.findIcon("ocd_run.png", OpenOcdConfigurationType.class);
        return icon == null ? ProductIcons.getInstance().getProductIcon() : icon;
    });
    private final ConfigurationFactory factory;

    public OpenOcdConfigurationType() {
        super(TYPE_ID,
                FACTORY_ID,
                "OpenOCD Download & Run",
                "Downloads and Runs Embedded Applications using OpenOCD",
                ICON
        );
        factory = new ConfigurationFactory(this) {
            @NotNull
            @Override
            public RunConfiguration createTemplateConfiguration(@NotNull Project project) {
                return new OpenOcdConfiguration(project, factory, "");
            }

            @NotNull
            @Override
            public RunConfigurationSingletonPolicy getSingletonPolicy() {
                return RunConfigurationSingletonPolicy.SINGLE_INSTANCE_ONLY;
            }

            @NotNull
            @Override
            public String getId() {
                return FACTORY_ID;
            }
        };
    }

    @Override
    public OpenOcdConfigurationEditor createEditor(@NotNull Project project) {
        return new OpenOcdConfigurationEditor(project, getHelper(project));
    }

    @NotNull
    @Override
    protected OpenOcdConfiguration createRunConfiguration(@NotNull Project project,
                                                          @NotNull ConfigurationFactory configurationFactory) {
        return new OpenOcdConfiguration(project, factory, "");
    }
}
