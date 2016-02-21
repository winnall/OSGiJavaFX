package net.winnall.osgi.javafx;

/*
 * #%L
 * SL Main
 * %%
 * Copyright (C) 2015 - 2016 Stephen Winnall
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
import javafx.application.Application;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * DOCUMENT ME!
 *
 * @author $Author$
 * @version $Revision$
 */
@Component(
        service = JavaFXLauncher.class
)
public class JavaFXLauncher {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Activate
    protected void activate() {
        log.debug("Activating {}", getClass());
    }

    public <A extends Application> void launch(Class<A> applicationClass) {
        // launch JavaFX Application in a separate thread to avoid blocking
        // the start-up of the OSGi framework
        new Thread(
                () -> {
                    try {
                        Application.launch(applicationClass);
                    } catch (Exception ex) {
                        log.error("Cannot launch {} - {}", applicationClass, ex);
                    }
                }
        ).start();
    }
}
