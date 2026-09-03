-- OpenComputers 26.2 bootstrap.
-- The Java runtime installs the safe computer/component/filesystem APIs before this file runs.
local computer = require("computer")
local component = require("component")

local function boot()
  if computer.beep then computer.beep() end
  if component.list then
    for address, kind in component.list() do
      -- Hardware discovery is intentionally delegated to the component API.
      -- No host filesystem, reflection or process APIs are exposed here.
      if address and kind then break end
    end
  end
  return true
end

return boot()
