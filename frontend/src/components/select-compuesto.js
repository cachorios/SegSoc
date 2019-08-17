
import { PolymerElement } from '@polymer/polymer/polymer-element.js';
import '@vaadin/vaadin-text-field/src/vaadin-text-field.js';
import '@polymer/iron-icon/iron-icon.js';
import './../../styles/shared-styles.js';
import { html } from '@polymer/polymer/lib/utils/html-tag.js';

class SelectCompuesto extends PolymerElement {
    static get template() {
        return html`
    <style>
        :host {
            display: flex;
            flex-direction: row;
            align-items: baseline;
            justify-content: space-between;
        }

        iron-icon {
                cursor: pointer;
                text-align: center;
              }

        vaadin-text-field {
            /*//--vaadin-text-field-default-width: 6em;*/
            /*flex: auto;*/
            /*display: flex;*/
        }

    </style>


    <vaadin-text-field id="input" part="text-field"
            label="[[label]]"
            disabled=[[disabled]]
            pattern$="[[pattern]]"
            prevent-invalid-input
            readonly="[[!editable]]"
            style="width: {{widthInput}};  "  >
        <iron-icon slot="suffix" icon="vaadin:close-small"  on-click="clearClick"   style="display: {{clearDisplay}};" ></iron-icon>
        <iron-icon slot="suffix" icon="vaadin:angle-down"   on-click="findClick"    style="display: {{findDisplay}};" ></iron-icon>
        <iron-icon slot="suffix" icon="vaadin:eye"          on-click="verClick"     style="display: {{verDisplay}};" ></iron-icon>
    </vaadin-text-field>&nbsp;
    <vaadin-text-field value="[[leyenda]]" readonly="true" style="width: 100%;" >
    </vaadin-text-field>
`;
    }


    static get is() {
        return 'select-compuesto';
    }

        static get properties(){
            return {
                label: String,
                leyenda: String,
                widthInput:{
                    type: String,
                    value: "30%"
                },
                verDisplay: {
                    type: String,
                    value: "none"
                },
                findDisplay: {
                    type: String,
                    value: "none"
                },
                clearDisplay: {
                  type: String,
                  value: "none"
                }
            };
        }

}


window.customElements.define(SelectCompuesto.is, SelectCompuesto);


