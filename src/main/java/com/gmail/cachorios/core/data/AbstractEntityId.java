package com.gmail.cachorios.core.ui.data;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;


@MappedSuperclass
	public class AbstractEntityId implements EntidadInterface {

		@Id
		@GeneratedValue
		private Long id;

		@Version
		private int version;

		public Long getId() {
			return id;
		}

		public int getVersion() {
			return version;
		}

		@Override
		public boolean isNew() {
		return (getId() == null);
	}

		@Override
		public int hashCode() {
			if (id == null) {
				return super.hashCode();
			}

			return 31 + id.hashCode();
		}

		@Override
		public boolean equals(Object other) {
			if (id == null) {
				// New entities are only equal if the instance if the same
				return super.equals(other);
			}

			if (this == other) {
				return true;
			}
			if (!(other instanceof com.gmail.cachorios.core.ui.data.AbstractEntityId)) {
				return false;
			}
			return id.equals(((AbstractEntityId) other).id);
		}

	}