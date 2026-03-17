/*-
 * ========================LICENSE_START=================================
 * flyway-core
 * ========================================================================
 * Copyright (C) 2010 - 2026 Red Gate Software Ltd
 * ========================================================================
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
 * =========================LICENSE_END==================================
 */
package org.flywaydb.core.internal;

import java.util.Optional;

public enum Topic {
    // Help Links
    BASELINE_ON_MIGRATE("baseline-on-migrate"),

    // Redgate SQLFluff rules
    RG01("rules/RG01"),
    RG02("rules/RG02"),
    RG03("rules/RG03"),
    RG04("rules/RG04"),
    RG05("rules/RG05"),
    RG06("rules/RG06"),
    RG07("rules/RG07"),
    RG08("rules/RG08"),
    RG09("rules/RG09"),
    RG10("rules/RG10"),
    RG11("rules/RG11"),
    RG12("rules/RG12"),
    RG13("rules/RG13"),
    RG14("rules/RG14"),
    RG15("rules/RG15"),
    RG16("rules/RG16"),
    RG17("rules/RG17"),
    // Redgate Regex rules
    RX001("rules/RX001"),
    RX002("rules/RX002"),
    RX003("rules/RX003"),
    RX004("rules/RX004"),
    RX005("rules/RX005"),
    RX006("rules/RX006"),
    RX007("rules/RX007"),
    RX008("rules/RX008"),
    RX009("rules/RX009"),
    RX010("rules/RX010"),
    RX011("rules/RX011"),
    RX012("rules/RX012"),
    RX013("rules/RX013"),
    RX014("rules/RX014"),
    // SQLFluff Aliasing rules
    AL01("rules/AL01"),
    AL02("rules/AL02"),
    AL03("rules/AL03"),
    AL04("rules/AL04"),
    AL05("rules/AL05"),
    AL06("rules/AL06"),
    AL07("rules/AL07"),
    AL08("rules/AL08"),
    AL09("rules/AL09"),
    // SQLFluff Ambiguous rules
    AM01("rules/AM01"),
    AM02("rules/AM02"),
    AM03("rules/AM03"),
    AM04("rules/AM04"),
    AM05("rules/AM05"),
    AM06("rules/AM06"),
    AM07("rules/AM07"),
    AM08("rules/AM08"),
    AM09("rules/AM09"),
    // SQLFluff Capitalisation rules
    CP01("rules/CP01"),
    CP02("rules/CP02"),
    CP03("rules/CP03"),
    CP04("rules/CP04"),
    CP05("rules/CP05"),
    // SQLFluff Convention rules
    CV01("rules/CV01"),
    CV02("rules/CV02"),
    CV03("rules/CV03"),
    CV04("rules/CV04"),
    CV05("rules/CV05"),
    CV06("rules/CV06"),
    CV07("rules/CV07"),
    CV08("rules/CV08"),
    CV09("rules/CV09"),
    CV10("rules/CV10"),
    CV11("rules/CV11"),
    CV12("rules/CV12"),
    // SQLFluff Jinja rules
    JJ01("rules/JJ01"),
    // SQLFluff Layout rules
    LT01("rules/LT01"),
    LT02("rules/LT02"),
    LT03("rules/LT03"),
    LT04("rules/LT04"),
    LT05("rules/LT05"),
    LT06("rules/LT06"),
    LT07("rules/LT07"),
    LT08("rules/LT08"),
    LT09("rules/LT09"),
    LT10("rules/LT10"),
    LT11("rules/LT11"),
    LT12("rules/LT12"),
    LT13("rules/LT13"),
    LT14("rules/LT14"),
    LT15("rules/LT15"),
    // SQLFluff References rules
    RF01("rules/RF01"),
    RF02("rules/RF02"),
    RF03("rules/RF03"),
    RF04("rules/RF04"),
    RF05("rules/RF05"),
    RF06("rules/RF06"),
    // SQLFluff Structure rules
    ST01("rules/ST01"),
    ST02("rules/ST02"),
    ST03("rules/ST03"),
    ST04("rules/ST04"),
    ST05("rules/ST05"),
    ST06("rules/ST06"),
    ST07("rules/ST07"),
    ST08("rules/ST08"),
    ST09("rules/ST09"),
    ST10("rules/ST10"),
    ST11("rules/ST11"),
    ST12("rules/ST12"),
    // SQLFluff TSQL rules
    TQ01("rules/TQ01"),
    TQ02("rules/TQ02"),
    TQ03("rules/TQ03");

    private final String path;
    private final Optional<String> anchor;

    Topic(final String path, final String anchor) {
        this.path = path;
        this.anchor = Optional.of(anchor);
    }

    Topic(final String path) {
        this.path = path;
        this.anchor = Optional.empty();
    }

    public String getEndpoint() {
        return path + anchor.map(a -> "#" + a).orElse("");
    }
}
