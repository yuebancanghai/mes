-- table: materialflowresources_resource
-- last touched: 15.12.2015 by kasi

ALTER TABLE materialflowresources_resource ADD COLUMN quantityinadditionalunit numeric(14,5);

ALTER TABLE materialflowresources_resource ADD COLUMN additionalcode_id bigint;
ALTER TABLE materialflowresources_resource
  ADD CONSTRAINT resource_additionalcode_fkey FOREIGN KEY (additionalcode_id)
      REFERENCES basic_additionalcode (id) DEFERRABLE;

ALTER TABLE materialflowresources_resource ADD COLUMN conversion numeric(12,5);
ALTER TABLE materialflowresources_resource ALTER COLUMN conversion SET DEFAULT 0::numeric;

ALTER TABLE materialflowresources_resource ADD COLUMN palletnumber_id bigint;
ALTER TABLE materialflowresources_resource
  ADD CONSTRAINT resource_palletnumber_fkey FOREIGN KEY (palletnumber_id)
      REFERENCES basic_palletnumber (id) DEFERRABLE;

ALTER TABLE materialflowresources_resource ADD COLUMN typeofpallet character varying(255);

-- end

-- ESILCO-16
CREATE TABLE materialflowresources_documentpositionparameters
(
  id bigint NOT NULL,
  showstoragelocation boolean DEFAULT false,
  showadditionalcode boolean DEFAULT false,
  showproductiondate boolean DEFAULT false,
  showexpiratindate boolean DEFAULT false,
  showpallet boolean DEFAULT false,
  showtypeofpallet boolean DEFAULT false,
  showbatch boolean DEFAULT false,
  CONSTRAINT materialflowresources_documentpositionparameters_pkey PRIMARY KEY (id)
);

ALTER TABLE basic_parameter ADD COLUMN documentpositionparameters_id bigint;

ALTER TABLE basic_parameter
  ADD CONSTRAINT parammeter_documentpositionparameters_fkey FOREIGN KEY (documentpositionparameters_id)
      REFERENCES materialflowresources_documentpositionparameters (id) DEFERRABLE;
-- end