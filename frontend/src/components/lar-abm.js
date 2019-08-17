
import { PolymerElement } from '@polymer/polymer/polymer-element.js';
import '@vaadin/vaadin-grid/src/vaadin-grid.js';
import './search-bar.js';
import './utils-mixin.js';
import './../../styles/shared-styles.js';
import { html } from '@polymer/polymer/lib/utils/html-tag.js';

class Abm extends PolymerElement{
    static get template() {
        return html`
     <style include="shared-styles">
        :host {
            height: 100%;
            width: 100%; /* calc(100% - 2 * 1rem);*/
            --top-bar-height: 60px;
            --titulo-height: 30px;
            display: flex;
            flex-direction: column;

            /*padding: 0 0.5rem;*/
            min-height: 200px;
        }

        vaadin-grid {
            height: calc(100% - var(--top-bar-height));
            min-height: 155px;
        }
        [part="abm-content"]{
            height: calc(100% - var(--titulo-height));
            /*min-height: 180px;*/
        }
        .titulo_compuesto{
            display: flex;
            flex-direction: row;
            /*align-content: space-between;*/
            justify-content: space-between;
            width: 100%;

        }
        h4{
            height:  var(--titulo-height);
            margin-block-start: 0.8rem;
            margin-block-end: 0;
            width: auto;
        }

    </style>
    <div class="titulo_compuesto">
    <h4 id="titulo" part="titulo" theme="abm" ></h4>
        <vaadin-button id="accion" class="withpadre-action-btn" theme="primary">
                <iron-icon icon$="[[buttonIcon]]" slot="prefix"></iron-icon>
                [[buttonText]]
          </vaadin-button>
    </div>

    <div part="abm-content" theme="abm">
        <search-bar id="searchbar" part="searchbar" theme="abm"  visible="[[search_view]]" ></search-bar>
        <vaadin-grid id="grid" theme="lar" slot="grid"></vaadin-grid>
    </div>

`;
    }

    static get is() {
                return 'lar-abm';
    }

    static get properties() {
                    return {
                         buttonText: {
                                    type: String,
                                    value: "Nuevo Registro"
                                  },
                        buttonIcon: {
                                  type: String,
                                  value: 'vaadin:plus'
                            },
                         search_view:{
                             type: Boolean,
                             value: true
                         }
                    };

            }
    static get observers() {
                    return [
                      '_setView(search_view)'
                    ];
                  }

}
window.customElements.define(Abm.is, Abm);



