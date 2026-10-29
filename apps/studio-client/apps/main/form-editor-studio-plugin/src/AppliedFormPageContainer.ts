import ValueExpressionFactory from "@coremedia/studio-client.client-core/data/ValueExpressionFactory";
import Config from "@jangaroo/runtime/Config";
import ConfigUtils from "@jangaroo/runtime/ConfigUtils";
import StateFulCollapsiblePanel from "./components/StateFulCollapsiblePanel";
import ShowFormIssuesPlugin from "./plugins/ShowFormIssuesPlugin";
import AppliedFormPageContainerBase from "./AppliedFormPageContainerBase";
import PageElementEditor from "./elements/PageElementEditor";
import FormEditor_properties from "./bundles/FormEditor_properties";

interface AppliedFormPageContainerConfig extends Config<AppliedFormPageContainerBase> {
}

class AppliedFormPageContainer extends AppliedFormPageContainerBase {
  declare Config: AppliedFormPageContainerConfig;

  static override readonly xtype: string = "com.tallence.formeditor.studio.config.appliedFormPageContainer";

  constructor(config: Config<AppliedFormPageContainer> = null) {
    super((() => ConfigUtils.apply(Config(AppliedFormPageContainer, {

      items: [
        Config(StateFulCollapsiblePanel, {
          margin: "0",
          collapsible: false,
          itemId: AppliedFormPageContainerBase.FORM_ELEMENT_PANEL,
          componentCls: "form-page-properties-panel",
          animCollapse: false,
          title: FormEditor_properties.FormEditor_pages_properties_title,
          plugins: [
            Config(ShowFormIssuesPlugin, {
              issuesVE: config.bindTo.extendBy(["issues"]),
              propertyPathVE: ValueExpressionFactory.createFromValue(config.formElement.getPropertyPath()),
            }),
          ],
          items: [
            Config(PageElementEditor, {
              itemId: AppliedFormPageContainerBase.FORM_PAGE_EDITOR,
              bindTo: config.bindTo,
              forceReadOnlyValueExpression: config.forceReadOnlyValueExpression,
              formElement: config.formElement
            })
          ],
        })
      ],
    }), config))());
  }
}

export default AppliedFormPageContainer;
