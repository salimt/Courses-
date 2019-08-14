require 'rake'
require 'json'
require 'mongo'

module Test_utils

    #helper method to get a list of current collections
    def all_collections
        Mongoid.default_client.collections.map(&:name).sort
    end

    def clear_all_collections
        Mongoid.default_client.collections.each{ |c|
            c.delete_many
        }
    end

	#helper method to determine if Ruby class exists as a class
    def class_exists?(class_name)
      eval("defined?(#{class_name}) && #{class_name}.is_a?(Class)") == true
    end

    def init_race
      # pastCount and futureCount are sanity checks to ensure
      # that we have generated races in the future and the past
      # if either is zero, rerun loop
      pastCount = 0
      futureCount = 0
      while (pastCount == 0 || futureCount == 0)
    	  Race.collection.delete_many
        (0..99).each {|r|
            # for purposes of testing, create 50 races in past
            # and 50 races from current time into future
            currentYear = Time.now.year
            if (r % 2 == 0) then
              year = Random.new.rand((currentYear - 5)..currentYear)
            else
              year = Random.new.rand(currentYear..(currentYear + 5))
            end
            month = Random.new.rand(1..12)
            day = Random.new.rand(1..28)
            date = Date.new(year, month, day)
            if (date > Time.now) then
              futureCount = futureCount + 1
            end
            if (date < Time.now) then
              pastCount = pastCount + 1
            end
            swim_distance = [100.0, 200.0, 400.0, 800.0, 1000.0, 5000.0].sample
            bike_distance = [1.0, 5.0, 10.0, 15.0, 25.0].sample
            run_distance = [1.0, 5.0, 10.0, 26.0].sample

            Race.create(name:"Name_#{r}", date:date, city:"City_#{r}", state:"State_#{r}", swim_distance:swim_distance,
                        swim_units:"meters", bike_distance:bike_distance, bike_units:"kilometers", run_distance:run_distance,
                        run_units:"miles")
        }
      end
    end

    def init_racer
    	Racer.collection.delete_many
    	(0..99).each { |r|
            year = Random.new.rand(1950..2002)
            gender = ["M", "F"].sample
            Racer.create(first_name:"First_#{r}", last_name:"Last_#{r}", gender:gender, birth_year:year, city:"City_#{r}", state:"State_#{r}")
        }
    end

    # method to generate data files used for web_resource tests
    # delete from bootstrap!!
    def init_complete_race
        clear_all_collections
        init_race1
        init_racer1
        race = Race.first
        racers = Racer.all.to_a
        (0..[racers.length, 5].min).each { |r|
            race.create_entrant(racers[r])
        }
        entrants = race.entrants
        m_place_index = 0
        f_place_index = 0
        place_index = 0
        total_time = 10000.0
        entrants.each { |e|
            e.swim_secs=Random.new.rand(500..1000).to_f
            e.bike_secs=Random.new.rand(3000..4000).to_f
            e.t1_secs=Random.new.rand(100..200).to_f
            e.t2_secs=Random.new.rand(100..200).to_f
            e.run_secs=total_time-e.swim_secs-e.bike_secs-e.t1_secs-e.t2_secs
            total_time = total_time + Random.new.rand(10).to_f
            e.overall = Placing.demongoize(place:place_index)
            e.group = Placing.demongoize(place:place_index)
            place_index = place_index + 1
            if (e.racer_gender=="M")
                e.gender = Placing.demongoize(place:m_place_index)
                m_place_index = m_place_index + 1
            else
                e.gender = Placing.demongoize(place:f_place_index)
                f_place_index = f_place_index + 1
            end
            e.touch
        }
        db = Mongoid.default_client.database
        system("mongoexport --db=#{db.name} --collection=races -o spec/data/races.json")
        system("mongoexport --db=#{db.name} --collection=racers -o spec/data/racers.json")
        system("mongoexport --db=#{db.name} --collection=results -o spec/data/results.json")
        race.id
    end

    def init_results_from_file
        db = Mongoid.default_client.database
        #system("mongoimport --db=#{db.name} --collection=results --drop --quiet spec/data/results.json")
        db[:races].find.delete_many
        File.open("./spec/data/races.json","r").each_line do |line|
          race=JSON.parse(line)
          race["_id"]=BSON::ObjectId.from_string(race["_id"]["$oid"])
          race["date"]=Date.xmlschema(race["date"]["$date"])
          race["created_at"]=DateTime.xmlschema(race["created_at"]["$date"])
          race["updated_at"]=DateTime.xmlschema(race["updated_at"]["$date"])
          race["events"].each do |event|
            event["_id"]=BSON::ObjectId.from_string(event["_id"]["$oid"])
          end
          db[:races].insert_one(race)
        end
    end

    def init_races_from_file
        db = Mongoid.default_client.database
        #system("mongoimport --db=#{db.name} --collection=races --drop --quiet spec/data/races.json")
        db[:racers].find.delete_many
        File.open("./spec/data/racers.json","r").each_line do |line|
          racer=JSON.parse(line)
          racer["_id"]=BSON::ObjectId.from_string(racer["_id"]["$oid"])
          racer["info"]["racer_id"]=BSON::ObjectId.from_string(racer["info"]["racer_id"]["$oid"])
          db[:racers].insert_one(racer)
        end
    end

    def init_racers_from_file
        db = Mongoid.default_client.database
        #system("mongoimport --db=#{db.name} --collection=racers --drop --quiet spec/data/racers.json")
        db[:results].find.delete_many
        File.open("./spec/data/results.json","r").each_line do |line|
          entrant=JSON.parse(line)
          entrant["_id"]=BSON::ObjectId.from_string(entrant["_id"]["$oid"])
          entrant["created_at"]=DateTime.xmlschema(entrant["created_at"]["$date"])
          entrant["updated_at"]=DateTime.xmlschema(entrant["updated_at"]["$date"])
          entrant["racer"]["racer_id"]=BSON::ObjectId.from_string(entrant["racer"]["racer_id"]["$oid"])
          entrant["racer"]["_id"]=BSON::ObjectId.from_string(entrant["racer"]["_id"]["$oid"])
          entrant["race"]["_id"]=BSON::ObjectId.from_string(entrant["race"]["_id"]["$oid"])
          entrant["race"]["date"]=DateTime.xmlschema(entrant["race"]["date"]["$date"])
          if (entrant["results"] != nil) then
            entrant["results"].map do |r|
                r["_id"] = BSON::ObjectId.from_string(r["_id"]["$oid"])
                r["event"]["_id"] = BSON::ObjectId.from_string(r["event"]["_id"]["$oid"])
            end
          end
          db[:results].insert_one(entrant)
        end
    end

    def adjust_dates
      db = Mongoid.default_client.database
      now = Date.current
      #NOW = Date.current
      max_date=db[:races].find.aggregate([ {:$group=>{:_id=>1, :max_date=>{:$max=>"$date"}}} ]).first[:max_date]
      # increment delta by one to ensure that at least one of the
      # dates falls in the future and satisfies the "upcoming" requirement
      delta_years=now.year - max_date.year + 1

      db[:races].find.each do |race|
        race_date = race[:date] + delta_years.years
        db[:races].find(:_id=>race[:_id]).update_one(:$set=>{:date=>race_date})
        db[:results].find(:"race._id"=>race[:_id]).update_many(:$set=>{:"race.date"=>race_date})
      end

      db[:racers].find.update_many(:$inc=>{:"race.birth_years"=>delta_years})

      db[:races].find.update_many(:$set=>{:created_at=>now, :updated_at=>now})
      db[:results].find.update_many(:$set=>{:created_at=>now, :updated_at=>now})
    end

    def init_mongo_db
        clear_all_collections
    	init_races_from_file
    	init_racers_from_file
    	init_results_from_file
      adjust_dates
	end

    RACE_FIELDS = { name:"Oakland Triathlon", date:Date.new(2016, 8, 1),
                    lat:37.8, lon:-122.2, city:"Oakland", state:"CA" }
    RACE_LOC = { type:"Point", coordinates:[RACE_FIELDS[:lon], RACE_FIELDS[:lat]]}
    RACE_ADDR = { city:RACE_FIELDS[:city], state:RACE_FIELDS[:state], loc:RACE_LOC}
    RACER_FIELDS = { lat:39.2, lon:-76.61, city:"Baltimore", state:"MD",
                       fname:"Edgar", lname:"Poe", byear:1990, gender:"M" }
    RACER_LOC = { type:"Point", coordinates:[RACER_FIELDS[:lon], RACER_FIELDS[:lat]]}
    RACER_ADDR = { city:RACER_FIELDS[:city], state:RACER_FIELDS[:state], loc:RACER_LOC}
    EVENT_NAMES = ["swim", "t1", "run", "t2", "bike"]
    EVENT_DIST = [1, nil, 10, nil, 25]
    EVENT_UNITS = ["miles", nil, "miles", nil, "kilometers"]
    RESULT_SECS = [60.13, 1600]

    def setup_data_for_testing
        Race.collection.delete_many
        Racer.collection.delete_many
        RacerInfo.collection.delete_many
        Event.collection.delete_many
        Entrant.collection.delete_many

        # set up Racer, RacerInfo
        racer = Racer.new
        racer.build_info(fn:RACER_FIELDS[:fname], ln:RACER_FIELDS[:lname],
                            g:RACER_FIELDS[:gender], yr:RACER_FIELDS[:byear],
                            res:Address.demongoize(RACER_ADDR))
        racer.save

        # set up Race, Events
        race = Race.create(n:RACE_FIELDS[:name], date:RACE_FIELDS[:date], loc:RACE_ADDR)
        (0..EVENT_NAMES.count-1).each { |i|
            race.events.build(o:i, n:EVENT_NAMES[i], d:EVENT_DIST[i], u:EVENT_UNITS[i])
        }
        race.save
    end

    def format_hours secs
        Time.at(secs).utc.strftime("%k:%M:%S") if secs
    end

    def format_minutes secs
        Time.at(secs).utc.strftime("%M:%S") if secs
    end

    def format_mph mph
        mph.round(1) if mph
    end

end
