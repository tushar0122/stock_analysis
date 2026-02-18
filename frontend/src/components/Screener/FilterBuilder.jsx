import React from 'react';
import { RATIO_FIELDS, COMPARISON_OPERATORS, LOGICAL_OPERATORS } from '../../utils/constants';
import '../../styles/filterbuild.css';

const FilterBuilder = ({ filters, setFilters }) => {
  const handleOperatorChange = (e) => {
    setFilters({ ...filters, operator: e.target.value });
  };

  const handleConditionChange = (index, field, value) => {
    const updatedConditions = [...filters.conditions];
    updatedConditions[index] = { ...updatedConditions[index], [field]: value };
    setFilters({ ...filters, conditions: updatedConditions });
  };

  const handleAddCondition = () => {
    setFilters({
      ...filters,
      conditions: [...filters.conditions, { field: 'roe', comparison: '>', value: '', period: 'current' }]
    });
  };

  const handleRemoveCondition = (index) => {
    const updatedConditions = filters.conditions.filter((_, i) => i !== index);
    setFilters({ ...filters, conditions: updatedConditions });
  };

  const allFields = Object.values(RATIO_FIELDS).flat();

  return (
    <div className="filter-builder">
      <h2>Build Filters</h2>

      {filters.conditions.length > 1 && (
        <div className="operator-selector">
          <label>Operator:</label>
          <select value={filters.operator} onChange={handleOperatorChange}>
            {LOGICAL_OPERATORS.map(op => (
              <option key={op.value} value={op.value}>{op.label}</option>
            ))}
          </select>
        </div>
      )}

      <div className="conditions-list">
        {filters.conditions.map((condition, index) => (
          <div key={index} className="condition-row">
            <select
              value={condition.field}
              onChange={(e) => handleConditionChange(index, 'field', e.target.value)}
              className="field-select"
            >
              {allFields.map(field => (
                <option key={field.key} value={field.key}>{field.label}</option>
              ))}
            </select>

            <select
              value={condition.comparison}
              onChange={(e) => handleConditionChange(index, 'comparison', e.target.value)}
              className="comparison-select"
            >
              {COMPARISON_OPERATORS.map(op => (
                <option key={op.value} value={op.value}>{op.label}</option>
              ))}
            </select>

            <input
              type="number"
              value={condition.value}
              onChange={(e) => handleConditionChange(index, 'value', e.target.value)}
              placeholder="Value"
              className="value-input"
              step="0.01"
            />

            {filters.conditions.length > 1 && (
              <button
                onClick={() => handleRemoveCondition(index)}
                className="btn-remove"
              >
                Remove
              </button>
            )}
          </div>
        ))}
      </div>

      <button onClick={handleAddCondition} className="btn-add-condition">
        + Add Condition
      </button>
    </div>
  );
};

export default FilterBuilder;
